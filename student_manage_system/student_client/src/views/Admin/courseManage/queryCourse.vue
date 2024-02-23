<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
            <el-form-item label="Course number" prop="cid">
              <el-input v-model.number="ruleForm.cid"></el-input>
            </el-form-item>
            <el-form-item label="Course name" prop="cname">
              <el-input v-model="ruleForm.cname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy query" prop="fuzzy">
              <el-switch v-model="ruleForm.fuzzy"></el-switch>
            </el-form-item>
            <el-form-item label="Minimum number of credits allowed" prop="lowBound">
              <el-input v-model.number="ruleForm.lowBound"></el-input>
            </el-form-item>
            <el-form-item label="Maximum number of credits allowed" prop="highBound">
              <el-input v-model.number="ruleForm.highBound"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">Reset</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card>
          <course-list :ruleForm="ruleForm" :isActive="true"></course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import CourseList from "@/views/Admin/courseManage/courseList";
export default {
  components: {CourseList},
  data() {
    return {
      ruleForm: {
        cid: null,
        cname: null,
        fuzzy: true,
        lowBound: null,
        highBound: null
      },
      rules: {
        cid: [
          { type: 'number', message: 'This type must be numeric.' }
        ],
        cname: [
        ],
        lowBound: [
          { type: 'number', message: 'This type must be numeric.' }
        ],
        highBound: [
          { type: 'number', message: 'This type must be numeric.' }
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
