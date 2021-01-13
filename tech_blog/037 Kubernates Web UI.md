## 指路牌
* Kubernetes

## 环境
* Mac
* Docker Desktop(with Kubernetes)

## 参考资料
[kubernetes.io](https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/)
[github-kubernetes/dashboard](https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md)

## 步骤
#### 获取Kubernetes Dashboard UI
```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-beta8/aio/deploy/recommended.yaml
```
> 最新命令参考[官网](https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/)

#### 创建账号
* 创建文件dashboard-adminuser.yaml，写入
```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin-user
  namespace: kubernetes-dashboard
```
* 创建文件dashboard-clusterrolebinding.yaml, 写入
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: admin-user
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: admin-user
  namespace: kubernetes-dashboard
```
* 执行
```bash
kubectl apply -f dashboard-adminuser.yaml 
kubectl apply -f dashboard-clusterrolebinding.yaml
```

* 获取token
```bash
Name:         admin-user-token-v57nw
Namespace:    kubernetes-dashboard
Labels:       <none>
Annotations:  kubernetes.io/service-account.name: admin-user
              kubernetes.io/service-account.uid: 0303243c-4040-4a58-8a47-849ee9ba79c1

Type:  kubernetes.io/service-account-token

Data
====
ca.crt:     1066 bytes
namespace:  20 bytes
token:      !! HERE IS TOKEN !!
```
> 获取后最好单独保存一下
> 最新步骤参考[官网](https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md)

## 启动
* 启动
```bash
kubectl proxy
```

* 访问：http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/.

* 贴入token
![04c263917af86a1b500fed08bfedd060.png](evernotecid://304A294F-4F6F-4959-9F4A-FB7A23735E12/appyinxiangcom/21327510/ENResource/p63)

* 完成
![85df87906b64ebbbb7be3ae144dba730.png](evernotecid://304A294F-4F6F-4959-9F4A-FB7A23735E12/appyinxiangcom/21327510/ENResource/p64)


## 后记
流程不是很复杂， 但是官方把流程分别写在了两个不同的地方，此处仅整理简化，文档随时会更新，请以官方为准，2020-04-13