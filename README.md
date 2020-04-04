# Talos - a BPMS-to-RPAS bridge system
Talos is a tool that acts as bridge between robotic process automation systems and business process management systems. It allows BPM systems to use RPA systems as delegates for the execution of certain tasks.

### Usage
Talos is available as maven depencency (see [packages](https://github.com/LeonBein/Talos/packages)). It provides [`TalosCore`](Talos/src/main/java/de/hpi/bpt/talos/TalosCore.java) as main class. This core class provides the `runProcess` method that can be called from an adapter on BPMS side to execute an RPA process. The core then directs the execution of this process via an adapter that conforms the [`RPASAdapter`](Talos/src/main/java/de/hpi/bpt/talos/RPAAdapter.java) interface.

RPAS adapters are loaded to Talos via Java 9 [ServiceLoader](https://docs.oracle.com/javase/9/docs/api/java/util/ServiceLoader.html) and Jigsaw Modules. Projects that want to provide an adapter implementation (e.g. with class "`AdapterClass`") must include `provides RPASAdapter with AdapterClass;` in their respective `module-info.java`. The adapter class can additionally be annotated with `@ProviderName`. This allows the class to be found by a different identifier than class name.

### Examples
An example implementation for the RPASAdapter can be found in the [`TalosUiPath`](TalosUiPath) subproject, which implements the adapter for the UiPath RPAS. More information can be found in its subfolder, and its artifact can be found as maven package under [packages](https://github.com/LeonBein/Talos/packages).  

An example usage of Talos as whole can be found in the [`TalosCamunda`](TalosCamunda) subproject of this repository. It includes an implementation of a BPMS adapter for the Camunda BPMN Workflow Engine and uses the UiPath RPAS adapter implementation. A prepackaged .war can be downloaded under [packages](https://github.com/LeonBein/Talos/packages) and is ready for deployment to Camunda.
