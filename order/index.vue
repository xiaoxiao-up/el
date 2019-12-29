<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
          <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
        </el-select>
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="注单号" prop="orderId">
            <el-input v-model="form.orderId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="form.userName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="彩票类型">
            <el-input v-model="form.type" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="玩法" prop="playName">
            <el-input v-model="form.playName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="彩票期号" prop="issue">
            <el-input v-model="form.issue" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="开奖结果" prop="open">
            <el-input v-model="form.open" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="投注内容">
            <el-input v-model="form.content" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="倍数">
            <el-input v-model="form.multiple" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="注数" prop="number">
            <el-input v-model="form.number" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="投注金额" prop="amount">
            <el-input v-model="form.amount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="中奖金额" prop="winAmount">
            <el-input v-model="form.winAmount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="状态0-不开启，1-开启" prop="stat">
            <el-input v-model="form.stat" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('orderId')" prop="orderId" label="注单号" />
        <el-table-column v-if="columns.visible('userName')" prop="userName" label="用户名" />
        <el-table-column v-if="columns.visible('type')" prop="type" label="彩票类型" />
        <el-table-column v-if="columns.visible('playName')" prop="playName" label="玩法" />
        <el-table-column v-if="columns.visible('issue')" prop="issue" label="彩票期号" />
        <el-table-column v-if="columns.visible('open')" prop="open" label="开奖结果" />
        <el-table-column v-if="columns.visible('content')" prop="content" label="投注内容" />
        <el-table-column v-if="columns.visible('multiple')" prop="multiple" label="倍数" />
        <el-table-column v-if="columns.visible('number')" prop="number" label="注数" />
        <el-table-column v-if="columns.visible('amount')" prop="amount" label="投注金额" />
        <el-table-column v-if="columns.visible('winAmount')" prop="winAmount" label="中奖金额" />
        <el-table-column v-if="columns.visible('stat')" prop="stat" label="状态0-不开启，1-开启" />
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('updateTime')" prop="updateTime" label="更新时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','order:edit','order:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudOrder from '@/api/order'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: 'order', url: 'api/order', sort: 'id,desc', crudMethod: { ...crudOrder }})
const defaultForm = { id: null, orderId: null, userName: null, type: null, playName: null, issue: null, open: null, content: null, multiple: null, number: null, amount: null, winAmount: null, stat: null, createTime: null, updateTime: null }
export default {
  name: 'Order',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'order:add'],
        edit: ['admin', 'order:edit'],
        del: ['admin', 'order:del']
      },
      rules: {
        orderId: [
          { required: true, message: '注单号不能为空', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        playName: [
          { required: true, message: '玩法不能为空', trigger: 'blur' }
        ],
        issue: [
          { required: true, message: '彩票期号不能为空', trigger: 'blur' }
        ],
        open: [
          { required: true, message: '开奖结果不能为空', trigger: 'blur' }
        ],
        number: [
          { required: true, message: '注数不能为空', trigger: 'blur' }
        ],
        amount: [
          { required: true, message: '投注金额不能为空', trigger: 'blur' }
        ],
        winAmount: [
          { required: true, message: '中奖金额不能为空', trigger: 'blur' }
        ],
        stat: [
          { required: true, message: '状态0-不开启，1-开启不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'orderId', display_name: '注单号' },
        { key: 'issue', display_name: '彩票期号' }
      ]
    }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      const query = this.query
      if (query.type && query.value) {
        this.crud.params[query.type] = query.value
      }
      return true
    }
  }
}
</script>

<style scoped>

</style>
