<template>
  <el-card>
    <el-descriptions title="User Information">
      <el-descriptions-item label="User ID">{{ id }}</el-descriptions-item>
      <el-descriptions-item label="Username">{{ name }}</el-descriptions-item>
      <el-descriptions-item label="User Type">
        <el-tag size="small">{{ type }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script>
export default {
  name: "infoCard",
  data() {
    return {
      id: sessionStorage.getItem('type') === 'teacher' ? sessionStorage.getItem('tid') : sessionStorage.getItem('sid'),
      name: sessionStorage.getItem('name'),
      type: sessionStorage.getItem('type')
    }
  }
}
</script>

<style scoped>

</style>
