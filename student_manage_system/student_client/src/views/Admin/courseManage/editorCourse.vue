<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="Course name" prop="cname">
        <el-input v-model="ruleForm.cname" :value="ruleForm.cname"></el-input>
      </el-form-item>
      <el-form-item label="Credit" prop="ccredit">
        <el-input v-model.number="ruleForm.ccredit" :value="ruleForm.ccredit"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">Submit</el-button>
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
        cid: null,
        cname: null,
        ccredit: null
      },
      rules: {
        cname: [
          { required: true, message: 'Input a course name: ', trigger: 'blur' },
        ],
        ccredit: [
          { required: true, message: 'Input the course credit: ', trigger: 'change' },
          { type: 'number', message: 'Input the course number: ', trigger: 'change'},
        ],
      }
    };
  },
  created() {
    const that = this
    if (this.$route.query.cid === undefined) {
      this.ruleForm.cid = 1
    }
    else {
      this.ruleForm.cid = this.$route.query.cid
    }
    axios.get('http://localhost:10086/course/findById/' + this.ruleForm.cid).then(function (resp) {
      that.ruleForm = resp.data[0]
      console.log(that.ruleForm)
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // To certify this program in front end.
          const that = this
          console.log(this.ruleForm)
          axios.post("http://localhost:10086/course/updateCourse", this.ruleForm).then(function (resp) {
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: 'Edit successfully.',
                type: 'success'
              });
            }
            else {
              that.$message.error('Fail to edit, check the database please.');
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
