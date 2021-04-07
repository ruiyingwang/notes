
## 基本组成
* .git
* .gitignore

## Branch
* 切换远程分支`a`
* 查看一个分支的父分支`git reflog show BRANCH_NAME`
* 

#### Stash

#### 配置
* 查看当前虽有配置 `git config -l`

2、httpBuffer加大    
git config --global http.postBuffer 524288000

3、压缩配置
git config --global core.compression -1    

4、修改配置文件
export GIT_TRACE_PACKET=1
export GIT_TRACE=1
export GIT_CURL_VERBOSE=1
以上配置文件修改，也可以大幅度提升git 克隆速度

#### 使用远程分支覆盖本地分支
```
git checkout develop
git branch
git branch -D temp

git fetch origin temp:temp
```


* [查看已暂存和未暂存的修改](https://www.cnblogs.com/beenupper/p/12526914.html)
* [Git 如何放弃所有本地修改](https://www.cnblogs.com/chenjo/p/11398357.html)
* [使用 Git 時如何做出跨 repo 的 cherry-pick](https://blog.m157q.tw/posts/2017/12/30/git-cross-repo-cherry-pick/)


<meta http-equiv="refresh" content="5">
