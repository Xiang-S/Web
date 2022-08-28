<template>
  <div class="form">
    <div class="link">
      <router-link to="/service/login" class="title" >
        登录
      </router-link>
      <router-link to="/service/register" class="title">
        注册
      </router-link>
      <div class="link_bar" ref="bar"></div>
    </div>
    <transition :name="transitionName" mode="out-in">
    <router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: "Service",
  data(){
    return{
      transitionName:''
    }
  },
  watch:{
    $route(to,from){
      if(to.meta.index===1){
        this.$refs.bar.style.left = '0px',
        this.transitionName='login'
      }
      if(to.meta.index===2){
        this.$refs.bar.style.left = '44px',
        this.transitionName='register'
      }
    }
  },
  methods: {

  }
}
</script>

<style scoped>
* {
  margin: auto;
}

.form {
  overflow: hidden;
  position: relative;
  background-color: #fff;
  width: 350px;
  border-radius: 4px;
  margin-top: 30px;
  box-shadow: 0 20px 50px 0 hsl(0deg 0% 64% / 10%);
}


.link {
  margin-left: 30px;
  position: relative;
}

.link_bar {
  position: absolute;
  left: 0px;
  top: 45px;
  background-color: #409EFF;;
  height: 4px;
  border-radius: 2px;
  width: 38px;
  transition: left .3s;
  z-index: 1;
}

.title {
  line-height: 60px;
  margin-right: 10px;
  text-decoration: none;
  color: #333;
  font-weight: 500;
}
.login-enter-active{
  animation: move .3s;
}
.register-enter-active{
  animation: out .3s;
}
@keyframes move {
  from{
    transform: translateX(-100%);
  }
  to{
    transform: translateX(0);
  }
}
@keyframes out {
  from{
    transform:translateX(100%);
  }
  to{
    transform:translateX(0);
  }
}
</style>