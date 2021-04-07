## common
```bash
export https_proxy="proxy.hkg.sap.corp:8080"
export http_proxy="proxy.hkg.sap.corp:8080"

export KUBECONFIG=/etc/kubernetes/kubeconfig/kubelet.kubeconfig
kubectl --kubeconfig=KUBE_CONFIG -n NAME_SPACE|--all-namespaces

kubectl get|edit|describe|logs cm|pod|networkpolicy
kubectl exec -it log-forwarder-6f6d5c7f45-pqqkn -c log-forwarder -n=logging -- sh

kubectl cp fluent-bit-26tbf:/fluent-bit/etc/ -c fluent-bit -n logging /Users/i353667/Downloads
```

## 概念
* 
    * node: “物理节点”实体机器或VM
    * lable: 贴标示，可用于node, 
    * pod: kubernetes 中最小的编排单位, 内部有1～n个容器组成 
    * Service: 后端真实服务的抽象，一个 Service 可以代表多个相同的后端服务,代表多个container
    * Ingress: 反向代理规则
    * ingress controller: 反向代理程序, 负责解析 Ingress 的反向代理规则
        * Deployment -> Service(LoadBalancer)
        * Deployment -> Service(NodePort)(kubectl get svc)
        * DaemonSet -> nodeSelector -> hostPort
    * controller: 用于管理pod
    * Deployment: 用于部署pod?
    * Service: 服务的网络层抽象
    * Namespace: 逻辑隔离, 鉴权, 资源管理
    * operator:
    * configmap:

## 更新configmap
可以使用edit命令直接修改，但是要解决修改被捕捉到问题，也许需要一个operator来更新pod


<meta http-equiv="refresh" content="3">