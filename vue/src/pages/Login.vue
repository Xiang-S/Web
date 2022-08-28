<template>
  <div class="form">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
      <el-form-item prop="username" class="items">
        <el-input type="text" v-model="ruleForm.username" autocomplete="off" prefix-icon="el-icon-user-solid"
                  placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="password" class="items">
        <el-input type="password" v-model="ruleForm.password" autocomplete="off" show-password
                  prefix-icon="el-icon-lock" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm" style="margin: 30px 115px;">登录</el-button>
      </el-form-item>
    </el-form>
<!--    <div>{{ date }}</div>-->
  </div>
</template>

<script>
import axios from 'axios'
import router from "@/router";

export default {
  name: "Login",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    var validateUser = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        username: '',
        password: ''
      },
      // date: '',
      rules: {
        username: [
          {require: true, validator: validateUser, trigger: 'blur'}
        ],
        password: [
          {require: true, validator: validatePass, trigger: 'blur'}
        ],
      }
    };
  },
  methods: {
    submitForm() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          axios.post('api/users/login', this.ruleForm).then((res) => {
            if (res.data.flag) {
              this.$message({
                message: '登录成功',
                type: "success"
              })
              localStorage.setItem('token', JSON.stringify(res.data))
              router.push('/home')
            } else {
              this.$message.error('账号或密码错误')
            }
          })
        } else {
          return false;
        }
      });
    },
  },
  mounted() {
    // var timer = setInterval(() => {
    //   var temp = new Date();
    //   var hour = temp.getHours() < 10 ? "0" + temp.getHours() : temp.getHours()
    //   var minute = temp.getMinutes() < 10 ? "0" + temp.getMinutes() : temp.getMinutes()
    //   var second = temp.getSeconds() < 10 ? "0" + temp.getSeconds() : temp.getSeconds()
    //   this.date = hour + ":" + minute + ":" + second
    // })
    // this.$once('hook:beforeDestroy', () => {
    //   clearInterval(timer)
    //   timer = null
    // })
  },
}
</script>

<style scoped>
* {
  margin: auto;
}

.form {
  width: 350px;
  background-color: #fff;
  border-radius: 4px;
  margin-top: 30px;
  padding: 4px 4px;
}

.items {
  margin-top: 30px;
}

.demo-ruleForm {
  margin: 100px auto;
  width: 300px;
}

</style>
