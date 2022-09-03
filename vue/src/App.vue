<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
  data(){
    return{
      transitionName:''
    }
  },
  watch:{
    $route(to,from){
      if(to.meta.index===1){
        this.transitionName='slide'
      }
    }
  },
  created() {
    if(this.$route.path !== "/service/login"){
      this.$router.replace("/service/login")
    }
  },
  mounted() {
    localStorage.removeItem("token")
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
html,
body {
  margin: 0;
  height: 100%;
  background-color: #F5F5F5;
  overflow-y: hidden;
}

</style>
