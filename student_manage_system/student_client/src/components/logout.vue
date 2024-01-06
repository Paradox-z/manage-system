<template>
  <div>
      <el-popconfirm
          confirm-button-text='Yes'
          cancel-button-text='No'
          icon="el-icon-info"
          icon-color="red"
          title="Confirm to log out?"
          @confirm="out"
      >
        <el-button slot="reference" type="danger" style="margin-top: 10px" round @click>
          logout
        </el-button>
      </el-popconfirm>
  </div>
</template>

<script>
export default {
  name: "logout",
  methods: {
    out() {
      sessionStorage.removeItem("token")
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>

</style>
