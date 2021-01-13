## 指路牌
符合一下关键词，这篇博客有可能会对你有帮助
* 不使用工厂函数的Flask应用
* 不使用蓝本的Flask应用
* Flask跨域配置
* 基于Token的登录状态管理
* Flask+Vue
* Vue路由拦截
* Axios 钩子

## 适用场景
这是一篇个人博客搭建的记录博客，也是一篇关于Flask和Vue的简单"工具书"，最后的代码会包含Web开发中常用的功能。(不全，只是使用频率相对高的)

## 环境
* 系统: 无关
* Flask(Python3)
* Vue(Node.js)

## 参考
《Flask Web开发 基于Python的Web应用开发实战》
[Vue.js](https://cn.vuejs.org/)

## 背景
个人博客的解决方案那么多，为什么我要自己再搭建一个呢？
其实搭建个人博客的目的并不是为了写博客...否则直接使用WordPress了，个人博客只是我想要实践自己学的技术，同时考虑到以后可能会加入负载均衡、集群等技术，导致架构大改，或者尝试实现语音控制等新玩法，一行一行码出来的在操作的可行性上必然是更好的。

## 代码功能
博客功能尚不健全，只实现了以下的基本功能
前端：注册登陆，博客创建(markdown编辑器)，首页拉取所有文章，创建博客需要登陆状态。
后端：以上服务需要的视图函数，配置跨域，令牌管理与验证，数据库管理。

出于记录的分享的目的，将实现登录状态管理的代码整理如下

## 实现思路
要实现基于令牌的登录状态管理，其思路大致如下
1. 前端将帐号密码提交后台
2. 后台验证，通过这返回token
3. 前端在每次请求前将token设置到请求头当中(使用axios钩子)
4. 后台在受保护的视图函数被调用时获取请求头的token，并验证token，若无问题则允许调用

这是一个大致的思路，后续调用手保护的视图函数部分，无论是让前后端完成什么操作，都可以执行根据需要实现。
以下部分将根据以上思路的顺序，展示主要代码，最后将贴出完成代码。

## 具体步骤
#### Flask配置跨域
前后端分离首选需要配置跨域，此处采用后端解决的方案，使用flask_cors库，代码如下：
> 由于会前端在获取token后会在每次HTTP请求时将token设置在头部，我给出的命名为'token'，若使用了其他名称，需在'Access-Control-Allow-Headers'中替换

```python
from flask_cors import CORS

CORS(app,supports_credentials=True)
@app.after_request
def after_request(resp):
	resp = make_response(resp)
	resp.headers['Access-Control-Allow-Origin'] = 'http://127.0.0.1:8080'
	resp.headers['Access-Control-Allow-Methods'] = 'GET,POST'
	resp.headers['Access-Control-Allow-Headers'] = 'content-type,token'
	return resp
```

#### Vue通过axios向flask发起登录请求
前端将获取的帐号密码传递给后台，将请求获取的token写入Vuex中。(Vuex中会将token写入localStorage)
```js
let _this = this
axios.post('http://127.0.0.1:5000/login',{
	username:this.username,
	password:this.password,
})
.then(function(response){
	let token = response.data
	_this.changeLogin({Authorization: token})
})
.catch(function(error){
})
```

#### Flask实现视图函数
视图函数将通过用户名和密码，验证用户信息，并生成token，返回token。
```py
# Routes
@app.route('/login',methods=['POST'])
def login():
	json = request.get_json()
	user = User.query.filter_by(username = json['username']).first()
	if user.verify_password(json['password']):
		g.currnet_user = user
		token = user.generate_auth_token(expiration=3600)
		return token
	return "wrong password"
```

#### Vue配置Axios钩子
配置Axios钩子，在每次HTTP请求的头部都添加token
```js
axios.interceptors.request.use(
	config => {
		let token = localStorage.getItem('Authorization');
		if(token){
			config.headers.common['token'] = token
		}
		return config
	},
	err => {
		return Promise.reject(err);
	});
```

#### 实现HTTPBasicAuth
flask_httpauth模块实现的功能很少，其核心部分是我们需要自己实现@auth.verify_password这个回调函数，当被@auth.login_required修饰的视图函数被访问时，会先执行回调函数，在回调函数中将获取http头部的token，并验证该token是否合法，若合法则允许访问。
```python
from flask_httpauth import HTTPBasicAuth
auth = HTTPBasicAuth()

@auth.verify_password
def verify_password(username_token):
	username_token = request.headers.get('Token')
	if username_token == '':
		return False
	else:
		g.currnet_user = User.verify_auth_token(username_token)
		g.token_used = True
		return g.currnet_user is not None


@auth.login_required
@app.route('/creatpost',methods=['POST'])
def new_post():
	json = request.get_json()
	newpost = Post(title=json['title'],content=json['content'])
	db.session.add(newpost)
	db.session.commit()
	return "200 OK"
```

#### 备注
以上部分即是实现基于令牌管理的代码核心部分，阅读以上代码知晓思路即可，由于其还调用了诸如ORM中的函数的原因，所以只有以上部分代码功能并不健全，请参考下面简化后的完整代码。


## 完整代码
**强调**：以下代码出于简化的目的，皆为实现功能的最基本码，并没有遵循各种规范。

#### Flask
```py
import os
from flask import Flask,make_response,render_template,redirect,url_for,jsonify,g,current_app,request,session
from flask_cors import CORS
from flask_sqlalchemy import SQLAlchemy
from flask_httpauth import HTTPBasicAuth
from flask_login import login_user,UserMixin,LoginManager,login_required
from werkzeug.security import generate_password_hash,check_password_hash
from itsdangerous import TimedJSONWebSignatureSerializer as Serializer

basedir = os.path.abspath(os.path.dirname(__file__))

# SQLite
app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret-key'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir,'data.sqlite')
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

# CORS
CORS(app,supports_credentials=True)
@app.after_request
def after_request(resp):
	resp = make_response(resp)
	resp.headers['Access-Control-Allow-Origin'] = 'http://127.0.0.1:8080'
	resp.headers['Access-Control-Allow-Methods'] = 'GET,POST'
	resp.headers['Access-Control-Allow-Headers'] = 'content-type,token'
	return resp

# Http auth
auth = HTTPBasicAuth()

@auth.verify_password
def verify_password(username_token):
	username_token = request.headers.get('Token')
	if username_token == '':
		return False
	else:
		g.currnet_user = User.verify_auth_token(username_token)
		g.token_used = True
		return g.currnet_user is not None

@auth.error_handler
def auth_error():
	return unauthorized('Invalid credentials')

# Routes
@app.route('/login',methods=['POST'])
def login():
	json = request.get_json()
	user = User.query.filter_by(username = json['username']).first()
	if user.verify_password(json['password']):
		g.currnet_user = user
		token = user.generate_auth_token(expiration=3600)
		return token
	return "wrong password"

@app.route('/register',methods=['POST'])
def register():
	json = request.get_json()
	email = json['username'] + '@email.com'
	user = User(email=email,username=json['username'],password=json['password'])
	db.session.add(user)
	db.session.commit()
	return "200 OK register"


@app.route('/postlist')
def article():
	ptemp = Post.query.all()
	return jsonify({
			'posts': [post.to_json() for post in ptemp],
		})

@auth.login_required
@app.route('/creatpost',methods=['POST'])
def new_post():
	json = request.get_json()
	newpost = Post(title=json['title'],content=json['content'])
	db.session.add(newpost)
	db.session.commit()
	return "200 OK"

def unauthorized(message):
    response = jsonify({'error': 'unauthorized', 'message': message})
    response.status_code = 401
    return response

# ORM
class User(UserMixin,db.Model):
	__tablename__ = 'users'
	id = db.Column(db.Integer, primary_key=True)
	email = db.Column(db.String(64),unique=True,index=True)
	username = db.Column(db.String(64),unique=True,index=True)
	password_hash = db.Column(db.String(128))

	@property
	def password(self):
		raise AttributeError('password is not a readable attribute')

	@password.setter
	def password(self,password):
		self.password_hash = generate_password_hash(password)

	def verify_password(self,password):
		return check_password_hash(self.password_hash,password)

	def generate_auth_token(self,expiration):
		s = Serializer(current_app.config['SECRET_KEY'],expires_in = expiration)
		return  s.dumps({'id':self.id}).decode('utf-8')

	@staticmethod
	def verify_auth_token(token):
		s = Serializer(current_app.config['SECRET_KEY'])
		try:
			data = s.loads(token)
		except:
			return None
		return User.query.get(data['id'])

class Post(db.Model):
	__tablename__ = 'posts'
	id = db.Column(db.Integer, primary_key=True)
	title = db.Column(db.String(64),unique=True,index=True)
	content = db.Column(db.String(64))

	def to_json(self):
		json_post = {
			'title': self.title,
			'content': self.content,
		}
		return json_post

if __name__ == '__main__':
	db.drop_all()
	db.create_all()
	app.run()
```

#### Vue -- main.js
```js
import Vue from 'vue';
import App from './App.vue';
import VueRouter from 'vue-router';
import router from './router';
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import axios from 'axios';
import vueAxios from 'vue-axios';
import store from './store';
import Vuex from 'vuex'

Vue.config.productionTip = false

Vue.use(VueRouter)
Vue.use(iView)
Vue.use(vueAxios,axios)
Vue.use(Vuex)

router.afterEach(route=>{
	window.scroll(0,0);
})

router.beforeEach((to,from,next)=>{
	let token = localStorage.getItem('Authorization');
	if(!to.meta.isLogin){
		next()
	}else{
		let token = localStorage.getItem('Authorization');
		if(token == null || token == ''){
			next('/')
		}else{
			next()
		}
	}
})

axios.interceptors.request.use(
	config => {
		let token = localStorage.getItem('Authorization');
		if(token){
			config.headers.common['token'] = token
		}
		return config
	},
	err => {
		return Promise.reject(err);
	});


new Vue({
  el:'#app',
  render: h => h(App),
  router,
  store,
})

```

#### Vue -- Vuex
```js
import Vue from 'vue';
import Vuex from 'vuex';
import store from './index';

Vue.use(Vuex);

export default new Vuex.Store({
	state:{
		Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : ''
	},
	mutations:{
		changeLogin (state, user) {
			state.Authorization = user.Authorization;
			localStorage.setItem('Authorization', user.Authorization);
		}
	},
})
```

#### Vue -- router
```js
import Vue from 'vue'
import Router from 'vue-router'

import home from '../components/home.vue'
import articleDetail from '../components/articleDetail'
import createPost from '../components/createPost'

Vue.use(Router)
export default new Router({
	mode:'history',
	routes:[
		{
			path:'/',
			component:home,
			name:'home',
			meta:{
				isLogin:false
			}
		},
		{
			path:'/article',
			component:articleDetail,
			name:'article',
			meta:{
				isLogin:false
			}
		},
		{
			path:'/createpost',
			component:createPost,
			name:'createpost',
			meta:{
				isLogin:true
			}
		},
	]
})
```

#### Vue -- Components -- home.vue
```js
<template>
	<div class="super">
		<div class="header">
			<div class="buttomDiv">
				<Button type="success" class="loginButton" @click="showLoginModal">Login</Button>
				<Button type="primary" class="loginButton" @click="showRegisterModal">Register</Button>
			</div>
		</div>

		<div class = "content">
			<div class="contentLeft">
				<div
					v-for = "post in blogList"
					>
					<thumbnail 
						v-bind:title=post.title
						v-bind:content=post.content
					></thumbnail>
				</div>
			</div>
			<div class="contentRight"></div>
			
		</div>

		<Modal v-model="registerModalStatus" @on-ok="registerEvent">
			<p>Register</p>
			<Input v-model="username" placeholder="Username" style="width: 300px" />
			<Input v-model="password" placeholder="Password" style="width: 300px" />
		</Modal>

		<Modal v-model="loginModalStatus" @on-ok="loginEvent">
			<p>Login</p>
			<Input v-model="username" placeholder="Username" style="width: 300px" />
			<Input v-model="password" placeholder="Password" style="width: 300px" />
		</Modal>

	</div>
</template>

<script>
	import axios from 'axios'
	import {mapMutations} from 'vuex'
	import store from '../store'
	import thumbnail from './articleThumbnail.vue'

	export default{
		name: 'home',
		data:function(){
			return {
				loginModalStatus:false,
				registerModalStatus:false,
				username:'',
				password:'',
				blogList:'',
			}
		},
		components:{
			thumbnail:thumbnail,
		},
		created(){
			localStorage.removeItem("Authorization","")
			let _this = this
			axios.get('http://127.0.0.1:5000/postlist')
					.then(function(response){
						_this.blogList = response.data.posts
					})
					.catch(function(error){
					})
		},
		methods:{
			...mapMutations([
				'changeLogin'
			]),
			showRegisterModal:function(){
				this.registerModalStatus = true;
			},
			showLoginModal:function(){
				this.loginModalStatus = true;
			},
			registerEvent:function(){
				let that = this
				axios.post('http://127.0.0.1:5000/register',{
					username:this.username,
					password:this.password,
					})
				.then(function(res){

				})
				.catch(function(error){

				})
			},
			loginEvent:function(){
				let _this = this
				axios.post('http://127.0.0.1:5000/login',{
							username:this.username,
							password:this.password,
						})
					.then(function(response){
						let token = response.data
						_this.changeLogin({Authorization: token})
					})
					.catch(function(error){
					})
			},
			navigator:function(){
				this.$router.push("/article")
			},

		},
	}
</script>

<style scoped>

</style>

```

## 后记
完整代码github地址
[haythamBlog](https://github.com/haytham331/haythamBlog)
[haythamBlog_flask](https://github.com/haytham331/haythamBlog_flask)


