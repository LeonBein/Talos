# Talos RPAS Adapter for UiPath
This is an example implementation for the Talos [`RPASAdapter`](Talos/src/main/java/de/hpi/bpt/talos/RPAAdapter.java) interface. 
It is built for the UiPath RPAS system.

To allow this adapter to connect to UiPath Orchestrator, a `.uipathconfig` file needs to be present in the main folder of the project that is run. It should look like the following:
```javascript
{
	"accountName" : $YOUR_ACCOUNT_NAME$,
	"tenantName" : $YOUR_TENANT_NAME$,
	"clientId" : $YOUR_CLIENT_ID$,
	"userKey" : $YOUR_USER_KEY$
}
```
