<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="Course name" prop="cname">
        <el-input v-model="ruleForm.cname"></el-input>
      </el-form-item>
      <el-form-item label="Credit" prop="ccredit">
        <el-input v-model.number="ruleForm.ccredit"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">Commit</el-button>
        <el-button @click="resetForm('ruleForm')">Reset</el-button>
        <el-button @click="test">test</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      ruleForm: {
        cname: null,
        ccredit: null
      },
      rules: {
        cname: [
          { required: true, message: 'Please enter a course-name: ', trigger: 'blur' },
        ],
        ccredit: [
          { required: true, message: 'Please enter the credit: ', trigger: 'change' },
          { type: 'number', message: 'Please enter an identified number: ', trigger: 'blur' },
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // Front-end proofread
          const that = this
          // console.log(this.ruleForm)

          axios.post("http://localhost:10086/course/save", this.ruleForm).then(function (resp) {
            console.log(resp)
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: 'Insert successfully',
                type: 'success'
              });
            }
            else {
              that.$message.error('Fail to insert, please check the linked database./t');
            }
            that.$router.push("/queryCourse")
          })
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    test() {
      console.log(this.ruleForm)
    }
  }
}
</script>
