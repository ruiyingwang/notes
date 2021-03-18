

```bash
export KUBECONFIG=/etc/kubernetes/kubeconfig/kubelet.kubeconfig

kubectl get|edit|describe|logs 
            cm|pod|networkpolicy
        exec -it log-forwarder-6f6d5c7f45-pqqkn -c log-forwarder -- sh
        cp fluent-bit-26tbf:/fluent-bit/etc/ -c fluent-bit /Users/i353667/Downloads
            -n NAME_SPACE|--all-namespaces
            --kubeconfig=KUBE_CONFIG
```

```

kubectl cluster-info --context kind-mtdevops
```