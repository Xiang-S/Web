<template>
  <div>
    <div class="app-container">

      <div class="box">

        <div class="filter-container">
          <el-input placeholder="图书类别" style="width: 200px;" class="filter-item"
                    v-model="pagination.type"></el-input>
          <el-input placeholder="图书名称" style="width: 200px;" class="filter-item"
                    v-model="pagination.name"></el-input>
          <el-input placeholder="图书描述" style="width: 200px;" class="filter-item"
                    v-model="pagination.description"></el-input>
          <el-button @click="getAll()" class="dalfBut">查询</el-button>
          <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
        </div>

        <el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>

          <el-table-column type="index" align="center" label="序号"></el-table-column>

          <el-table-column prop="type" label="图书类别" align="center"></el-table-column>

          <el-table-column prop="name" label="图书名称" align="center"></el-table-column>

          <el-table-column prop="description" label="描述" align="center"></el-table-column>

          <el-table-column label="操作" align="center">

            <template slot-scope="scope">

              <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>

              <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>

            </template>

          </el-table-column>

        </el-table>

        <!--分页组件-->
        <div class="pagination-container">

          <el-pagination
              class="pagiantion"

              @current-change="handleCurrentChange"

              :current-page="pagination.currentPage"

              :page-size="pagination.pageSize"

              layout="total, prev, pager, next, jumper"

              :total="pagination.total">

          </el-pagination>

        </div>

        <!-- 新增标签弹层 -->

        <div class="add-form">

          <el-dialog title="新增图书" :visible.sync="dialogFormVisible">

            <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right"
                     label-width="100px">

              <el-row>

                <el-col :span="12">

                  <el-form-item label="图书类别" prop="type">

                    <el-input v-model="formData.type"/>

                  </el-form-item>

                </el-col>

                <el-col :span="12">

                  <el-form-item label="图书名称" prop="name">

                    <el-input v-model="formData.name"/>

                  </el-form-item>

                </el-col>

              </el-row>


              <el-row>

                <el-col :span="24">

                  <el-form-item label="描述">

                    <el-input v-model="formData.description" type="textarea"></el-input>

                  </el-form-item>

                </el-col>

              </el-row>

            </el-form>

            <div slot="footer" class="dialog-footer">

              <el-button @click="cancel()">取消</el-button>

              <el-button type="primary" @click="handleAdd()">确定</el-button>

            </div>

          </el-dialog>

        </div>

        <!-- 编辑标签弹层 -->

        <div class="add-form">

          <el-dialog title="编辑检查项" :visible.sync="dialogFormVisible4Edit">

            <el-form ref="dataEditForm" :model="formData" :rules="rules" label-position="right"
                     label-width="100px">

              <el-row>

                <el-col :span="12">

                  <el-form-item label="图书类别" prop="type">

                    <el-input v-model="formData.type"/>

                  </el-form-item>

                </el-col>

                <el-col :span="12">

                  <el-form-item label="图书名称" prop="name">

                    <el-input v-model="formData.name"/>

                  </el-form-item>

                </el-col>

              </el-row>

              <el-row>

                <el-col :span="24">

                  <el-form-item label="描述">

                    <el-input v-model="formData.description" type="textarea"></el-input>

                  </el-form-item>

                </el-col>

              </el-row>

            </el-form>

            <div slot="footer" class="dialog-footer">

              <el-button @click="cancel()">取消</el-button>

              <el-button type="primary" @click="handleEdit()">确定</el-button>

            </div>

          </el-dialog>

        </div>

      </div>

    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Repertory",
  data() {
    return {
      dataList: [],//当前页要展示的列表数据
      dialogFormVisible: false,//添加表单是否可见
      dialogFormVisible4Edit: false,//编辑表单是否可见
      formData: {},//表单数据
      rules: {//校验规则
        type: [{required: true, message: '图书类别为必填项', trigger: 'blur'}],
        name: [{required: true, message: '图书名称为必填项', trigger: 'blur'}]
      },
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 5,//每页显示的记录数
        total: 0,//总记录数
        type: '',
        name: '',
        description: ''
      }
    }
  },

  //钩子函数，VUE对象初始化完成后自动执行
  created() {
    this.getAll();
  },

  methods: {
    //列表
    getAll() {
      axios.get("api/books/" + this.pagination.currentPage + "/" + this.pagination.pageSize + "?type=" + this.pagination.type + "&name=" + this.pagination.name + "&description=" + this.pagination.description).then((res) => {
        this.dataList = res.data.data.records
        this.pagination.currentPage = res.data.data.current
        this.pagination.pageSize = res.data.data.size
        this.pagination.total = res.data.data.total
      })
    },

    //弹出添加窗口
    handleCreate() {
      this.resetForm()
      this.dialogFormVisible = true;
    },

    //重置表单
    resetForm() {
      this.formData = {}
    },

    //添加
    handleAdd() {
      axios.post("api/books", this.formData).then((res) => {
        if (res.data.flag) {
          this.dialogFormVisible = false
          this.$message.success(res.data.msg)
        } else {
          this.$message.error(res.data.msg)
        }
      }).finally(() => {
        this.getAll()
      })
    },

    //取消
    cancel() {
      this.dialogFormVisible = false
      this.dialogFormVisible4Edit = false
    },
    // 删除
    handleDelete(row) {
      this.$confirm("确认删除?", "提示", {
        type: 'info'
      }).then(() => {
        axios.delete("api/books/" + row.id).then((res) => {
          if (res.data.flag) {
            this.$message.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
        }).finally(() => {
          this.getAll()
        })
      })

    },

    //弹出编辑窗口
    handleUpdate(row) {
      axios.get("api/books/" + row.id).then((res) => {
        if (res.data.flag && res.data.data != null) {
          this.dialogFormVisible4Edit = true
          this.formData = res.data.data
        } else {
          this.$message.error("数据同步失败")
        }
      }).finally(() => {
        this.getAll()
      })
    },

    //修改
    handleEdit() {
      axios.put("api/books", this.formData).then((res) => {
        if (res.data.flag) {
          this.dialogFormVisible4Edit = false
          this.$message.success(res.data.msg)
        } else {
          this.$message.error(res.data.msg)
        }
      }).finally(() => {
        this.getAll()
      })
    },

    //分页查询

    //切换页码
    handleCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage
      this.getAll()
    },
    //条件查询
  }
}
</script>

<style scoped>
html,body {

  /* overflow-y: scroll; */

  margin: 0;

}

a {

  color: #3c8dbc;

  text-decoration:none;

}

/* new style */

.skin-purple .main-sidebar {

  background: #fff;

}

.skin-purple .main-header .logo:hover {

  background: #0abdfe;

}

.skin-purple .main-header .navbar .sidebar-toggle:hover {

  /* background: #0abdfe; */

}

.skin-purple .main-header {

  min-height: 70px;

  padding: 0;

}

.skin-purple .main-header .logo {

  height: 50px;

  /* background: #0abdfe; */

  float: left;

  padding: 20px 0 0 15px;

  /* width: 230px; */

}

.skin-purple .main-header .navbar {

  height: 70px;

  background: linear-gradient(to right, #0abdfe, #67f0e0);

  /* margin-left: 230px; */

}

.winfo{margin-left: 230px;}

.skin-purple .main-header .sidebar-toggle {

  display: inline-block;

  padding: 24px 15px;

  color: #fff;

}

.skin-purple .main-sidebar {

  padding-top: 75px;

}

.sidebar-menu > li {

  line-height: 1.8

}

.skin-purple .sidebar-menu > li > a {

  font-size: 16px;

  color: #666

}

.skin-purple .sidebar-menu>li:hover>a,

.skin-purple .sidebar-menu>li.active>a {

  background: transparent;

  color: #666;

  border-left-color: transparent

}

.skin-purple .treeview-menu>li>a:hover {

  color: #fff

}

.skin-purple .sidebar-menu>li>.treeview-menu {

  background: #fff;

}

.sidebar-menu .treeview-menu > li > a {

  font-size: 16px;

  padding-left: 35px;

  color: #999

}

.sidebar-menu .treeview-menu > li:hover {

  background: #0abdfe;

}

@media (min-width: 768px) {

  .skin-purple  .navbar-nav>li>a

  {

    padding-top: 25px;

    padding-bottom: 25px;

  }

}

.modal-body .nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover {

  color: #0abdfe

}

.modal-body .nav-tabs>li>a {

  color: #555

}

.bg-olive {

  background-color: #0abdfe !important;

}

.dataTable .btn[class*='bg-']:hover {

  box-shadow: none

}

.btn-primary {

  background: #0abdfe;

  border-color: #0abdfe;

}

.box-body .nav>li>a {

  color: #666

}

.box-body .nav>li.active>a {

  color: #0abdfe;

}





/* tab 1*/

.double {

  line-height: 58px;

}

.title .glyphicon{

  padding: 3px;

  font-size: 13px;

  border-radius: 8px;

  color: #fff;



}

.data span.arrowup {

  color: #d88918;

}

.data span.arrowdown {

  color: #6bb10a;

}

.item-blue .glyphicon{

  background-color: #39a9ea;

}

.item-green {

  line-height: 58px;

}

.item-green .glyphicon{

  background-color: #6bb10a;

  line-height: 12px;

}

.item-orange .glyphicon{

  background-color:#d88918;

}

.item-red .glyphicon{

  background-color: #f14f4f;

}

.chart .chart-box {

  margin: 10px;

}



/* 数据表格label */

.content-wrapper .data-type {

  /*width: 90%;*/

  margin: 10px 5px;

  border:1px solid #d4d4d4;

  border-radius: 2px;

}

.data-type .title,

.data-type .data {

  padding: 3px 12px;

  border-top: 1px solid #d4d4d4;

  overflow: hidden;

  height: 42px;

}

.data-type .title {

  line-height: 34px;

  border-right: 1px solid #d4d4d4;

}



.data-type .data:last-child{

  border-right: 0;

}

.data-type .title{

  text-align: center;

  background: #ececec;

}

.data-type .data .line{

  vertical-align: middle;

  overflow: hidden;

  padding-bottom: 10px;

  padding-top: 10px;

}



/* label行高度 */

.data-type .data > label {

  line-height:36px;

}

.data-type .data > .form-group {

  line-height:36px;

}

.data-type .data.text {

  line-height:36px;

}

/* label行分隔符 */

.data-type .data.border-right {

  border-right: 1px solid #d4d4d4;

}



/* 表格双倍高度 */

.data-type .title.rowHeight2x,

.data-type .data.rowHeight2x {

  height:84px;

}

.data-type .title.rowHeight2x ,

.data-type .data.rowHeight2x.text {

  line-height:78px;

}

/*.data-type .data.rowHeight2x > label {

	line-height:78px;

}*/

.data-type .title.editer,

.data-type .data.editer {

  height:320px;

}

.data-type .title.editer {

  line-height:300px;

}



/*清除parding*/

.padding-clear {

  padding-right: 0px;

  padding-left: 0px;

}



/* 文件上传 */

/*a  upload */

.a-upload {

  padding: 4px 10px;

  height: 35px;

  line-height: 25px;

  position: relative;

  cursor: pointer;

  color: #888;

  background: #fafafa;

  border: 1px solid #ddd;

  border-radius: 4px;

  overflow: hidden;

  display: inline-block;

  *display: inline;

  *zoom: 1

}

.a-upload  input {

  position: absolute;

  font-size: 100px;

  right: 0;

  top: 0;

  opacity: 0;

  filter: alpha(opacity=0);

  cursor: pointer

}

.a-upload:hover {

  color: #444;

  background: #eee;

  border-color: #ccc;

  text-decoration: none

}

/* 医疗 */

.search-box {

  display: inline-block

}

.input-sm {

  height: 32px;

}

.btn-create {

  margin-left: 10px;

  background-color: #0abdfe;

  border-color: #0abdfe;

  color: #fff;

}

.btn-create:hover,

.btn-create:active,

.btn-create:focus

{

  color: #fff;

}

.pagination {

  margin: 0

}

.medical-modal {

  position:absolute;

  top:0%;

  left:0%;

  display:none;

  background:rgba(0,0,0,0.3);

  width:100%;

  height:100%;

  position:fixed;

  z-index:9999

}

.medical-modal .content {

  position: absolute;

  left: 35%;

  top: 25%;

  border-radius: 8px;

  width: 30%;

  height: 40%;

  background-color: #fff;

}

.pageitems, .jump {

  margin-left: 15px;

  display: inline-block;

}

.jumppage {

  width: 30px;

  text-align: center

}

@media (min-width: 768px) {

  .subscribe .modal-dialog {

    width: 900px;

    margin: 30px auto;

  }

}

.checklist {

  margin-top: 10px;

}

.checklist .input-group {

  margin-bottom: 10px;

}

.modal-page {

  margin-top: 20px;

  font-size: 12px;

}

/*.dalfBut{*/
/*  position: absolute;*/
/*  left: -180px;*/
/*}*/

/*.butT{*/
/*  position: absolute;*/
/*  left: -180px;*/
/*}*/

.modal-page .form-control {

  font-size: 12px;

  padding: 0;

  height: 26px;

}

.table-check {

  margin: 0;

  display: inline-block;

  margin-right: 4px;

}

.daterange {

  margin:10px 10px 0;

}

.filter-item{
  margin-left: 10px;

}

.daterange .input-group .form-control {

  width: 20%;

}

.chart-title {

  font-size: 16px;

  font-weight: normal;

  text-align: center;

}

.diaocha {

  line-height: 2

}

.diaocha h5{

  color: #f98d45;

  background: #f5f7f9;

  line-height: 2;

  padding-left: 15px;

}

.diaocha div {

  padding: 0 20px;

  border-bottom: 1px solid #dce1e7;

}

.diaocha div h5 {

  color: #555;

  background: transparent;

  padding-left: 0;

}

.diaocha label {

  font-weight: normal;

}

.diaocha .form-group {

  margin-left: 0;

  margin-right: 0;

}

.diaocha .options label {

  margin-right: 10px;

}



.tizhi button{

  margin-right: 15px;

}

.innerform {

  margin-top: 20px;

}

.fa-search {

  cursor: pointer

}

.line {

  margin-top: 10px;

}

input[type=radio]:focus {

  outline: none

}

input[type="radio"]{

  appearance: none;

  -webkit-appearance: none;

  outline: none;

  display:none

}

label input[type="radio"] {

  content: "\a0";

  display: inline-block;

  vertical-align: middle;

  font-size: 16px;

  width: 15px;

  height: 15px;

  margin-right: .4em;

  border-radius: 50%;

  border: 1px solid #c7c6c6;

  line-height: 1;

  margin-top: -1px;

}

label input[type="radio"]:checked {

  border: 3px solid #0abdfe;

}

.right-menu {

  float: right;

  padding: 18px 30px 0 0;

  color: #fff;

}

.el-dropdown{color: #fff;}

.avatar-wrapper img{width: 30px;height: 30px;border-radius: 15px;vertical-align: middle}

.el-popper[x-placement^=bottom]{margin-top: 30px;}

.el-dropdown-menu__item--divided{margin: 0;border:0 none;border-bottom: 1px solid #ebeef5}

.help{

  padding: 0 10px;

}

.help .fa{ margin-right: 5px;}

.el-main{

  background: #ecf0f5;

}

.el-menu{border: 0 none;}

.main{

  height: 100vh;

  min-width: 800px;

  min-height: 600px;

  overflow: hidden;

}

.main aside{

  overflow: visible;

  height: 100%;

}

.main aside.isClossTab{

  width: 100%;

  height: 60px;

  cursor: pointer;

  font-size: 25px;

  text-align: center;

  line-height: 60px;

  font-weight: bold;

  border-right: 1px solid #807c7c;

  box-sizing: border-box;

}

.main aside .menu{

  width: 100%;

  border-right:0;

}

.el-menu .fa{

  vertical-align: middle;

  margin-right: 5px;

  width: 24px;

  text-align: center;

  font-size: 18px;

}

.el-menu-item a{

  color: #303133

}

.el-menu-item:hover,.el-menu-item.is-active {

  color: #fff;

  background: #0abdfe;

}

.el-menu-item:hover a,.el-menu-item.is-active a{

  color: #fff;

}

.el-submenu__title:hover{background: none;}

.main-footer {

  background: #fff;

  padding: 15px 0;

  color: #444;

}

/* title */

.content-header {

  position: relative;

  padding: 15px 15px 0 15px;

  /* margin-top: 70px; */

}

.content-header > h1 {

  margin: 0;

  font-size: 24px;

  font-weight: normal;

}

.content-header > h1 > small {

  font-size: 15px;

  display: inline-block;

  padding-left: 4px;

  font-weight: 300;

}

.content-header > .breadcrumb {

  float: right;

  background: transparent;

  margin-top: 0;

  margin-bottom: 0;

  font-size: 12px;

  padding: 7px 5px;

  position: absolute;

  top: 20px;

  right: 10px;

  border-radius: 2px;

}

/*  */

.app-container{

  background: #fff;

  margin: 15px 30px 15px 15px;



}

.pagiantion{

  text-align: right;

  padding: 15px;

}

.box {

  position: relative;

  border-radius: 3px;

  background: #ffffff;

  border-top: 3px solid #3c8dbc;

  padding: 10px;

  margin-bottom: 20px;

  width: 100%;

  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);

}

.filter-container{


}

.main-container{margin-top: 70px;}

.filter-container .el-button,.filter-container .el-input__inner{

  padding: 0 15px;

  line-height: 34px;

  margin-left: 10px;

}

.el-aside{overflow: hidden;}

.el-submenu .el-menu-item a{

  display: block;

  height: 50px;

}

.el-menu--collapse .el-submenu__icon-arrow{ display: none}

/* .el-container{position: relative;} */

/* foot */

.el-footer{

  position: absolute;

  left: 180px;

  right: 0px;

  bottom: -80px;

}

.boxMain .el-upload--text{

  position:static;

}

.boxMain >div{

  display: inline-block;

}

.excelTitle{

  text-align: center;

  overflow: hidden;

  line-height: 40px;

}

.excelTitle .el-button{

  float: left;

}

.excelTime{

  padding: 10px 0;

  text-align: right;

}

.exceTable{

  width: 100%;

  border-right: 1px solid #e6e6e6;

  border-bottom: 1px solid #e6e6e6;

  font-size: 14px;

  color: #333;

}

.exceTable tr,.exceTable td{

  border-left: 1px solid #e6e6e6;

  border-top: 1px solid #e6e6e6;

  height: 40px;

  line-height: 40px;

  padding: 0 10px;

}

.exceTable .headBody{

  text-align: center;

  font-weight: 700;

  font-size: 14px;

}

.tabletrBg{

  background: #fcfcfc;

  text-align: right;

}

.textCenter{

  text-align: center

}

.checkScrol{

  height: 277px;

  overflow-y:scroll; ;

}

</style>
