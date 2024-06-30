<script setup lang='ts'>
import { useRoute, useRouter } from 'vue-router'
import {reactive, ref} from 'vue'
import * as path from "path";
import {ElMessage} from "element-plus";
import type {RegisterData} from "@/api/users/type";
import {reqUserLogin, reqUserRegister} from "@/api/users";

let registerParam = reactive<RegisterData>({
    password: "", role: 1, username: ""
})

const router = useRouter();

function handleSignin() {
  router.push({path: '/signin'})
}

async function handleRegistration() {

  if (!registerParam.username || registerParam.username.trim() === '') {
      ElMessage({
          type: 'error',
          message: '用户名不能为空'
      })
      return
  }

  if (!registerParam.password || registerParam.password.trim() === '') {
      ElMessage({
          type: 'error',
          message: '密码不能为空'
      })
      return
  }

    try {
        let result = await reqUserRegister(registerParam)
        console.log(result.data.password)
        ElMessage({
            type: 'success',
            message: "注册成功",
        })
        await router.push({path: '/signin'});
    } catch (e: Error) {
        console.log(e)
    }
  // fetch('http://api.uniconnector.com/portal/environments/DEFAULT/users/registration', {
  //   method: 'POST',
  //   headers: {
  //     'Content-Type': 'application/json'
  //   },
  //   body: JSON.stringify(data),
  // })
	// .then(response => response.text())
	// .catch(error => console.error('Error:', error))
	// .then(function (response) {
  //   router.push({ name:"chat" })
  // })
}
</script>
<template>
    <div id="layout" class="theme-cyan">
      <div class="authentication">
        <div class="container d-flex flex-column">
          <div class="row align-items-center justify-content-center no-gutters min-vh-100">
            <div class="col-12 col-md-7 col-lg-5 col-xl-4 py-md-11">
              <div class="card border-0 shadow-sm">
                <div class="card-body">
                  <h3 class="text-center">Sign up</h3>
                  <p class="text-center mb-6">Create your free Account</p>
                  <form class="mb-4 mt-5">
                    <div class="input-group mb-2">
                      <input type="text" class="form-control form-control-lg" v-model="registerParam.username" placeholder="Enter your username"></div>
                    <div class="input-group mb-2">
                      <input type="text" class="form-control form-control-lg" v-model="registerParam.password" placeholder="Enter your password"></div>
<!--                      <div class="input-group mb-4"></div>-->
<!--                      <input type="email" class="form-control form-control-lg" v-model="email" placeholder="Enter your email"></div>-->
                    <div class="text-center mt-4">
                      <a href="#" class="btn btn-lg btn-primary" @click="handleRegistration" title="">Sign up</a></div>
                  </form>
                  <p class="text-center mb-0">Already have an account?
                    <a class="link" href="#" @click="handleSignin()">Sign in</a>.</p>
                  <div class="mt-4 d-grid gap-2">
                    <button type="button" class="btn btn-block btn-outline-facebook">Signup with QQ</button>
                    <button type="button" class="btn btn-block btn-outline-success">Signup with Wechat</button></div>
                </div>
              </div>
            </div>
            <div class="signin-img d-none d-lg-block text-center">
              <img src="../../assets/signin-img-cyan.svg" alt="Sign In Image"></div>
          </div>
        </div>
      </div>
    </div>
</template>
