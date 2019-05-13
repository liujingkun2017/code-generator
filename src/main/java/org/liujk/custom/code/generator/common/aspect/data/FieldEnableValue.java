package org.liujk.custom.code.generator.common.aspect.data;

public class FieldEnableValue {

    //字段是否启用
    private boolean enable;

    //列表是否显示
    private boolean listEnable;

    //表单是否显示
    private boolean formEnable;

    //是否启用字典
    private boolean dicEnable;

    //字典编码
    private String dicCode;

    //字典项文本
    private String dicText;

    //数据字典表
    private String dictTable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isListEnable() {
        return listEnable;
    }

    public void setListEnable(boolean listEnable) {
        this.listEnable = listEnable;
    }

    public boolean isFormEnable() {
        return formEnable;
    }

    public void setFormEnable(boolean formEnable) {
        this.formEnable = formEnable;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicText() {
        return dicText;
    }

    public void setDicText(String dicText) {
        this.dicText = dicText;
    }

    public String getDictTable() {
        return dictTable;
    }

    public void setDictTable(String dictTable) {
        this.dictTable = dictTable;
    }

    public boolean isDicEnable() {
        return dicEnable;
    }

    public void setDicEnable(boolean dicEnable) {
        this.dicEnable = dicEnable;
    }
}
