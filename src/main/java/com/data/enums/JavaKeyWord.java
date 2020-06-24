package com.data.enums;

/**
 * @Description java的关键字
 * @Author tanlishuai
 * @Date 2020-06-24 15:43
 */
public enum JavaKeyWord {

    PUBLIC("public","public_"),
    CLASS("class","class_"),
    ENUM("enum","enum_"),
    PACKAGE("package","package_"),
    PRIVATE("private","private_"),
    PROTECTED("protected","protected"),
    DEFAULT("default","default_"),
    THIS("this","this_"),
    VOID("void","void_"),
    RETURN("return","return_"),
    STATIC("static","static_"),
    FINAL("final","final_"),
    TRY("try","try_"),
    CATCH("catch","catch_"),
    FINALLY("finally","finally_"),
    INTERFACE("interface","interface_"),
    IMPLEMENTS("implements","implements_"),
    EXTENDS("extends","extends_"),
    THROWS("throws","throws_"),
    THROW("throw","throw_"),
    NEW("new","new_"),
    ABSTRACT("abstract","abstract_"),
    CASE("case","case_"),
    CONTINUE("continue","continue_"),
    BREAK("break","break_"),
    FOR("for","for_"),
    INSTANCEOF("instanceof","instanceof_"),
    SWITCH("switch","switch_"),
    TRANSIENT("transient","transient_"),
    ASSERT("assert","assert_"),
    GOTO("goto","goto_"),
    INT("int","int_"),
    BYTE("byte","byte_"),
    SHORT("short","short_"),
    LONG("long","long_"),
    CHAR("char","char_"),
    BOOLEAN("boolean","boolean_"),
    DOUBLE("double","double_"),
    FLOAT("float","float_"),
    SYNCHRONIZED("synchronized","synchronized_"),
    DO("do","do_"),
    WHILE("while","while_"),
    IF("if","if_"),
    ELSE("else","else_"),
    STRICTFP("strictfp","strictfp_"),
    VOLATILE("volatile","volatile_"),
    CONST("const","const_"),
    IMPORT("import","import_"),
    NATIVE("native","native_"),
    SUPER("super","super_"),


    ;
    private String keyWord;
    private String changeName;

    JavaKeyWord(String keyWord,String changeName){
        this.keyWord=keyWord;
        this.changeName=changeName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }
}
