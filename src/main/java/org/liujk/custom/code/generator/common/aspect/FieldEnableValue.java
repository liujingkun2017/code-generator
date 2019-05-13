package org.liujk.custom.code.generator.common.aspect;

public class FieldEnableValue {

    //字段是否启用
    private boolean enable;

    //列表是否显示
    private boolean listEnable;

    //表单是否显示
    private boolean formEnable;

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
}
