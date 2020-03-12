# Talos - a BPMS-to-RPAS bridge system
Talos is a tool that acts as bridge between robotic process automation systmes and business process management systems. It allows BPM systems to use RPA systems as delegates for the execution of certain tasks.

### Usage
Talos is available as maven depencency (see [packages](https://github.com/LeonBein/Talos/packages)). It provides [`TalosCore`](Talos/src/main/java/de/hpi/bpt/talos/TalosCore.java) as main class. This core class provides the `runProcess` method that can be called from an adapter on BPMS side to execute an RPA process. The core then directs the execution of this process via an adapter that conforms the [`RPASAdapter`](Talos/src/main/java/de/hpi/bpt/talos/RPAAdapter.java) interface.

An example usage of Talos can be found in the [`TalosCamunda`](TalosCamunda) subproject of this repository. It includes an implementation of the BPMS adapter for the Camunda BPMN Workflow Engine and uses the RPAS adapter UiPath implementation. A prepackaged .war can be downloaded under [packages](https://github.com/LeonBein/Talos/packages) and is ready for deployment to Camunda.
