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
          label="Course number"
          width="150">
      </el-table-column>
      <el-table-column
          prop="cname"
          label="Course name"
          width="150">
      </el-table-column>
      <el-table-column
          prop="ccredit"
          label="Course credit"
          width="150">
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
              title="Deleted information that cannot be recovered."
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
      axios.get('http://localhost:10086/course/deleteById/' + row.cid).then(function (resp) {
        console.log(resp)
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: 'Delete successfully',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: 'Fail to delete, please check the link with database.',
            type: 'error'
          });
        }
      }).catch(function (error) {
        that.$message({
          showClose: true,
          message: 'Opps, there is a foreign key dependency cause deleted unsuccessfully.',
          type: 'error'
        });
      })
    },
    offer(row) {
      const tid = sessionStorage.getItem("tid")
      const cid = row.cid
      const term = sessionStorage.getItem("currentTerm")

      const that = this
      axios.get('http://localhost:10086/courseTeacher/insert/' + cid + '/' + tid + '/' + term).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '开设成功',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: '开设失败，请联系管理员',
            type: 'error'
          });
        }
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
        path: '/editorCourse',
        query: {
          cid: row.cid
        }
      })
    }
  },
  created() {
    console.log(this.type)
  },
  data() {
    return {
      tableData: null,
      pageSize: 10,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem("type"),
    }
  },
  props: {
    ruleForm: Object,
    isActive: Boolean
  },
  watch: {
    ruleForm: {
      handler(newRuleForm, oldRuleForm) {
        console.log("组件监听 form")
        console.log(newRuleForm)
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:10086/course/findBySearch", newRuleForm).then(function (resp) {
          console.log("查询结果:");
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
