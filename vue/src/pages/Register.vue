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
        <el-button type="primary" @click="submitForm" style="margin: 30px 115px;">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'
import router from "@/router";

export default {
  name: "Register",
  data() {
    const regExp = /^[0-9]+$/
    var validatePass = (rule, value, callback) => {
      if (regExp.test(value)) {
        callback(new Error('密码不能为纯数字'));
      } else {
        callback()
      }
    };
    var validateUser =  (rule, value, callback) => {
      if (value != '') {
        let params = {username: this.ruleForm.username}
        axios.post("api/users/checkUsername", params).then(
            res => {
              if (res.data.flag) {
                callback()
              } else {
                callback(new Error("用户名已存在"))
              }
            }
        )
      } else {
        callback()
      }
    }
    return {
      ruleForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {validator: validateUser, trigger: 'blur'},
          {
            min: 3,
            max: 16,
            message: '长度为3-16个字符',
            trigger: 'blur'
          }
        ],
        password: [
          {validator: validatePass, trigger: 'blur'},
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
      }
    };
  }
  ,
  methods: {
    submitForm() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          axios.post('api/users/register', this.ruleForm).then(
              res => {
                if (res.data.flag) {
                  this.$message({
                    message: '注册成功',
                    type: "success"
                  })
                  router.push('/home')
                }
              },
              err => {
                alert(err.message)
              }
          )
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
* {
  margin: auto;
}

.form {
  width: 350px;
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
