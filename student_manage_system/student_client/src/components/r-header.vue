<template>
  <div>

    <img style="float:left" src="../assets/logo.png" alt="" height="50px" />
    <span style=" font-size: 20px;float:left;color: #fff">
      Student Administration System
    </span>

      <el-divider direction="vertical" style="color: #fff"></el-divider>
      <i class="el-icon-paperclip" style="color: #fff;margin-right: 18px"></i>
      <span style="color: #fff">{{ currentTerm }}</span>
      <el-divider direction="vertical"></el-divider>
      <i class="el-icon-user" style="color: #fff;margin-right: 18px"></i>
      <span style="color: #fff">{{ name }}</span>
    <el-button type="info" style="color: #fff; font-size: 18px" @click="out()">
      logout
    </el-button>

  </div>
</template>

<script>
export default {
  name: "r-header",
  data() {
    return {
      name: null,
      currentTerm: null
    }
  },
  created() {
    this.name = sessionStorage.getItem("name")
    this.currentTerm = sessionStorage.getItem("currentTerm")
  },
  methods: {
    out() {
      sessionStorage.clear();
      this.$router.push('/')
    }
  }
}
</script>

<style lang="less" scoped>

</style>
