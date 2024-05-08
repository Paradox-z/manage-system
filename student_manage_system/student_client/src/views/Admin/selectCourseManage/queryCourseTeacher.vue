<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
            <el-form-item label="Work identity" prop="tid">
              <el-input v-model.number="ruleForm.tid"></el-input>
            </el-form-item>
            <el-form-item label="Teacher name" prop="tname">
              <el-input v-model.number="ruleForm.tname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy enquiry the teachers' list">
              <el-switch v-model="ruleForm.tFuzzy"></el-switch>
            </el-form-item>
            <el-form-item label="Course number" prop="cid">
              <el-input v-model.number="ruleForm.cid"></el-input>
            </el-form-item>
            <el-form-item label="Course name" prop="cname">
              <el-input v-model.number="ruleForm.cname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy enquiry for courses.">
              <el-switch v-model="ruleForm.cFuzzy"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">RESET</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top: 10px">
          <course-tacher-list :ruleForm="ruleForm"></course-tacher-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import CourseTacherList from "@/views/Admin/selectCourseManage/CourseTacherList";
export default {
  components: {CourseTacherList},
  data() {
    return {
      ruleForm: {
        tid: null,
        cid: null,
        cname: null,
        tname: null,
        tFuzzy: true,
        cFuzzy: true
      },
      rules: {
        tid: [
          { type: 'number', message: 'Need to be numeric.' }
        ],
        cid: [
          { type: 'number', message: 'Need to be numeric.' }
        ],
      }
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>
