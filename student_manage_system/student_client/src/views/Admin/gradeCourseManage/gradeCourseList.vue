<template>
  <div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          fixed
          prop="cid"
          label="Course ID"
          width="150">
      </el-table-column>
      <el-table-column
          prop="cname"
          label="Course name"
          width="150">
      </el-table-column>
      <el-table-column
          fixed
          prop="tid"
          label="Teacher number"
          width="100">
      </el-table-column>
      <el-table-column
          prop="tname"
          label="Teacher name"
          width="100">
      </el-table-column>
      <el-table-column
          fixed
          prop="sid"
          label="Student number"
          width="100">
      </el-table-column>
      <el-table-column
          prop="sname"
          label="Student name"
          width="100">
      </el-table-column>
      <el-table-column
          prop="grade"
          label="Mark"
          width="100">
      </el-table-column>
      <el-table-column
          prop="term"
          label="Semester"
          width="100">
      </el-table-column>
      <el-table-column
          label="Operation"
          width="100">
        <template slot-scope="scope">
          <el-popconfirm
              confirm-button-text='Delete'
              cancel-button-text='Cancel'
              icon="el-icon-info"
              icon-color="red"
              title="Irreversible deletion"
              @confirm="deleteTeacher(scope.row)"
          >
            <el-button slot="reference" type="text" size="small">Delete</el-button>
          </el-popconfirm>
          <el-button @click="editor(scope.row)" type="text" size="small">Edit</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        @current-change="changePage"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  methods: {
    select(row) {
      console.log(row)
    },
    deleteTeacher(row) {
      const that = this
      console.log(row)
      const sid = row.sid
      const cid = row.cid
      const tid = row.tid
      const term = row.term
      axios.get("http://localhost:10086/SCT/deleteById/" + sid + '/' + cid + '/' + tid + '/' + term).then(function (resp) {
        console.log(resp)
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: 'Successfully deleted',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: 'Please check the database connection before attempting to delete the file.',
            type: 'error'
          });
        }
      }).catch(function (error) {
        that.$message({
          showClose: true,
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
      })
    },
    changePage(page) {
      page = page - 1
      const that = this
      let start = page * that.pageSize, end = that.pageSize * (page + 1)
      let length = that.tmpList.length
      let ans = (end < length) ? end : length
      that.tableData = that.tmpList.slice(start, ans)
    },
    editor(row) {
      this.$router.push({
        path: '/editorGradeCourse',
        query: {
          cid: row.cid,
          tid: row.tid,
          sid: row.sid,
          term: row.term
        }
      })
    }
  },
  data() {
    return {
      tableData: null,
      pageSize: 10,
      total: null,
      tmpList: null,
    }
  },
  props: {
    ruleForm: Object,
  },
  watch: {
    ruleForm: {
      handler(newRuleForm, oldRuleForm) {
        console.log("Module Monitor form")
        console.log(newRuleForm)
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:10086/SCT/findBySearch", newRuleForm).then(function (resp) {
          console.log("Enquiry results:");
          console.log(resp)
          that.tmpList = resp.data
          that.total = resp.data.length
          let start = 0, end = that.pageSize
          let length = that.tmpList.length
          let ans = (end < length) ? end : length
          that.tableData = that.tmpList.slice(start, ans)
        })
      },
      deep: true,
      immediate: true
    }
  },
}
</script>
