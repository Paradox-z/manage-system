<template>
  <div>
    <el-card>
      <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="Course name" prop="cname">
          <el-input v-model="ruleForm.cname" :value="ruleForm.cname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="Teacher name" prop="tname">
          <el-input v-model="ruleForm.tname" :value="ruleForm.tname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="Student name" prop="sname">
          <el-input v-model="ruleForm.sname" :value="ruleForm.sname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="Grade" prop="grade">
          <el-input v-model.number="ruleForm.grade" :value="ruleForm.grade"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">Submit</el-button>
          <el-button @click="resetForm('ruleForm')">Reset</el-button>
          <el-button @click="test">test</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    var checkGrade = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('The grade column cannot be null.'));
      }
      if (!Number.isInteger(value)) {
        callback(new Error('Enter a number please.'));
      } else {
        if (value > 100 || value < 0) {
          callback(new Error('the range of grades [0 - 100]'));
        } else {
          callback();
        }
      }
    };
    return {
      ruleForm: {
        cid: null,
        cname: null,
        grade: null,
        sid: null,
        sname: null,
        tid: null,
        tname: null,
      },
      rules: {
        grade: [
          { required: true, message: 'Credit:', trigger: 'change'},
          { type: 'number', message: 'Number:', trigger: 'change'},
          { validator: checkGrade, trigger: 'blur'}
        ],
      }
    };
  },
  created() {
    const that = this
    this.ruleForm.cid = this.$route.query.cid
    this.ruleForm.tid = this.$route.query.tid
    this.ruleForm.sid = this.$route.query.sid
    this.ruleForm.term = this.$route.query.term
    axios.get('http://localhost:10086/SCT/findById/' +
        this.ruleForm.sid + '/' +
        this.ruleForm.cid + '/' +
        this.ruleForm.tid + '/' +
        this.ruleForm.term).then(function (resp) {
      that.ruleForm = resp.data
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          const sid = that.ruleForm.sid
          const cid = that.ruleForm.cid
          const tid = that.ruleForm.tid
          const term = that.ruleForm.term
          const grade = that.ruleForm.grade
          axios.get("http://localhost:10086/SCT/updateById/" + sid + '/' + cid + '/' + tid + '/' + term + '/' + grade).then(function (resp) {
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: 'Edit successfully.',
                type: 'success'
              });
            }
            else {
              that.$message.error('编辑失败，请检查数据库');
            }
            if (sessionStorage.getItem('type') === 'admin') {
              that.$router.push("/queryGradeCourse")
            } else {
              that.$router.push("/teacherQueryGradeCourseManage")
            }
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
