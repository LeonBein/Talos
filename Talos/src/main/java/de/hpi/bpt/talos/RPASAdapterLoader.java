package de.hpi.bpt.talos;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.hpi.bpt.talos.RPASAdapter.ProviderName;

@SuppressWarnings("rawtypes")
public class RPASAdapterLoader {
	
	public static void main(String[] args) {
		//assertNoDuplicates();
		System.out.println(get("TalosTesting").type());
		System.out.println(get("tAlOsTeStInG").type());
		System.out.println(get("MockAdapter").type());
		System.out.println(get("NotExisting").type());
	}
	
	public static Provider<RPASAdapter> get(String name) {
		return new RPASAdapterLoader().find(name);
	}
	
	public Provider<RPASAdapter> find(String name) {
		assertNoDuplicates();
		return findByProviderName(name)
			.orElseGet(() -> findByTypeName(name)
			.orElseThrow(() -> new RuntimeException("Cannot find RPAS provider \""+name+"\"")));	
	}
	
	public Optional<Provider<RPASAdapter>> findByProviderName(String providerName) {
		return findBy(providerName, RPASAdapterLoader::providerName);
	}
	
	public Optional<Provider<RPASAdapter>> findByTypeName(String typeName) {
		return findBy(typeName, RPASAdapterLoader::typeName);
	}
	
	public Optional<Provider<RPASAdapter>> findBy(String provider, Function<Provider<RPASAdapter>, Optional<String>> aspect) {
		Objects.requireNonNull(provider);
		return services().filter(each -> {
        	return provider.toLowerCase().equals(aspect.apply(each).map(String::toLowerCase).orElse(null));
		}).findAny();
	}
	
	public Stream<Provider<RPASAdapter>> services() {
        ServiceLoader<RPASAdapter> services = ServiceLoader.load(RPASAdapter.class);
        return services.stream();
	}
	
	public void assertNoDuplicates() {
		services()
			.filter(each -> Objects.nonNull(RPASAdapterLoader.providerName(each)))
			.collect(Collectors.groupingBy(RPASAdapterLoader::providerName))
			.entrySet().stream()
			.filter((any -> any.getValue().size() > 1))
			.findAny()
			.ifPresent(collision -> {
				throw new RuntimeException("Collision for provider name \""+collision.getKey()+"\": Implementing classes are "+collision.getValue().stream().map(Provider::type).collect(Collectors.toList()));
			});
		services()
			.collect(Collectors.groupingBy(RPASAdapterLoader::typeName))
			.entrySet().stream()
			.filter((any -> any.getValue().size() > 1))
			.findAny()
			.ifPresent(collision -> {
				throw new RuntimeException("Collision for type name \""+collision.getKey()+"\": Implementing classes are "+collision.getValue().stream().map(Provider::type).collect(Collectors.toList()));
			});
	}
	
	public static Optional<String> providerName(Provider<RPASAdapter> provider) {
    	Optional<ProviderName> name = Optional.ofNullable(provider.type().getAnnotation(ProviderName.class));
    	return name.map(ProviderName::value);
	}
	
	public static Optional<String> typeName(Provider<RPASAdapter> provider) {
    	return Optional.of(provider.type().getSimpleName());
	}

}
