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
        <el-date-picker
          v-model="query.openTime"
          :default-time="['00:00:00','23:59:59']"
          type="daterange"
          range-separator=":"
          size="small"
          class="date-item"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="openTimeStart"
          end-placeholder="openTimeEnd"
        />
        <el-date-picker
          v-model="query.createTime"
          :default-time="['00:00:00','23:59:59']"
          type="daterange"
          range-separator=":"
          size="small"
          class="date-item"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="createTimeStart"
          end-placeholder="createTimeEnd"
        />
        <el-date-picker
          v-model="query.updateTime"
          :default-time="['00:00:00','23:59:59']"
          type="daterange"
          range-separator=":"
          size="small"
          class="date-item"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="updateTimeStart"
          end-placeholder="updateTimeEnd"
        />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="彩票编码" prop="code">
            <el-input v-model="form.code" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="彩票名称" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="期数" prop="issue">
            <el-input v-model="form.issue" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="开奖时间" prop="openTime">
            <el-date-picker v-model="form.openTime" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="开奖号码" prop="openNumber">
            <el-input v-model="form.openNumber" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('code')" prop="code" label="彩票编码" />
        <el-table-column v-if="columns.visible('name')" prop="name" label="彩票名称" />
        <el-table-column v-if="columns.visible('issue')" prop="issue" label="期数" />
        <el-table-column v-if="columns.visible('openTime')" prop="openTime" label="开奖时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.openTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('openNumber')" prop="openNumber" label="开奖号码" />
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
        <el-table-column v-permission="['admin','lotteryOpen:edit','lotteryOpen:del']" label="操作" width="150px" align="center">
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
import crudLotteryOpen from '@/api/lotteryOpen'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '开奖管理', url: 'api/lotteryOpen', sort: 'id,desc', crudMethod: { ...crudLotteryOpen }})
const defaultForm = { id: null, code: null, name: null, issue: null, openTime: null, openNumber: null, number: null, amount: null, winAmount: null, stat: null, createTime: null, updateTime: null }
export default {
  name: 'LotteryOpen',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'lotteryOpen:add'],
        edit: ['admin', 'lotteryOpen:edit'],
        del: ['admin', 'lotteryOpen:del']
      },
      rules: {
        code: [
          { required: true, message: '彩票编码不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '彩票名称不能为空', trigger: 'blur' }
        ],
        issue: [
          { required: true, message: '期数不能为空', trigger: 'blur' }
        ],
        openTime: [
          { required: true, message: '开奖时间不能为空', trigger: 'blur' }
        ],
        openNumber: [
          { required: true, message: '开奖号码不能为空', trigger: 'blur' }
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
        { key: 'name', display_name: '彩票名称' },
        { key: 'issue', display_name: '期数' }
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
