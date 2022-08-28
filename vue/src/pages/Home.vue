<template>
  <div>
    <el-container>
      <el-container>
        <el-aside width="13%">
          <el-row class="tac">
            <el-col :span="24">
              <el-menu
                  default-active="2"
                  router
                  class="el-menu-vertical-demo"
                  @open="handleOpen"
                  @close="handleClose">
                <el-submenu index="1">
                  <template slot="title">
                    <i class="el-icon-s-home"></i>
                    <span>首页</span>
                  </template>
                  <el-menu-item-group>
                    <template slot="title">分组一</template>
                    <el-menu-item index="1-1">选项1</el-menu-item>
                    <el-menu-item index="1-2">选项2</el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group title="分组2">
                    <el-menu-item index="1-3">选项3</el-menu-item>
                  </el-menu-item-group>
                  <el-submenu index="1-4">
                    <template slot="title">选项4</template>
                    <el-menu-item index="1-4-1">选项1</el-menu-item>
                  </el-submenu>
                </el-submenu>
                <el-menu-item index="/home/repertory">
                  <i class="el-icon-truck"></i>
                  <span slot="title">库存</span>
                </el-menu-item>
                <el-menu-item index="3">
                  <i class="el-icon-s-custom"></i>
                  <span slot="title">个人信息</span>
                </el-menu-item>
                <el-menu-item index="4">
                  <i class="el-icon-setting"></i>
                  <span slot="title">设置</span>
                </el-menu-item>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "xiaomi",
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.el-aside {
  text-align: center;
  line-height: 200px;
}
.el-menu{
  height:100vh
}
.el-main {
  background-color: #FFF;
  color: #333;
  text-align: center;
  line-height: 110px;
}

</style>
