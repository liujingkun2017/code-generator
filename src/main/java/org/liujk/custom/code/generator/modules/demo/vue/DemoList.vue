<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="关键词">
              <a-input placeholder="请输入关键词" v-model="queryParam.keyWord"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="打卡时间">
              <a-input placeholder="请输入打卡时间" v-model="queryParam.punchTime"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="工资">
              <a-input placeholder="请输入工资" v-model="queryParam.salaryMoney"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="奖金">
              <a-input placeholder="请输入奖金" v-model="queryParam.bonusMoney"></a-input>
            </a-form-item>
          </a-col>
        </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <demo-modal ref="modalForm" @ok="modalFormOk"></demo-modal>
  </a-card>
</template>

<script>
  import DemoModal from './modules/DemoModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "DemoList",
    mixins:[JeecgListMixin],
    components: {
      DemoModal
    },
    data () {
      return {
        description: 'demo管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: '姓名',
            align:"center",
            dataIndex: 'name'
           },
		   {
            title: '关键词',
            align:"center",
            dataIndex: 'keyWord'
           },
		   {
            title: '打卡时间',
            align:"center",
            dataIndex: 'punchTime'
           },
		   {
            title: '工资',
            align:"center",
            dataIndex: 'salaryMoney'
           },
		   {
            title: '奖金',
            align:"center",
            dataIndex: 'bonusMoney'
           },
		   {
            title: '性别 {男:1,女:2}',
            align:"center",
            dataIndex: 'sex'
           },
		   {
            title: '年龄',
            align:"center",
            dataIndex: 'age'
           },
		   {
            title: '生日',
            align:"center",
            dataIndex: 'birthday'
           },
		   {
            title: '邮箱',
            align:"center",
            dataIndex: 'email'
           },
		   {
            title: '个人简介',
            align:"center",
            dataIndex: 'content'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/demo/demo/list",
          delete: "/demo/demo/delete",
          deleteBatch: "/demo/demo/deleteBatch",
          exportXlsUrl: "demo/demo/exportXls",
          importExcelUrl: "demo/demo/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }
  .ant-table-tbody .ant-table-row td{
    padding-top:15px;
    padding-bottom:15px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}
</style>