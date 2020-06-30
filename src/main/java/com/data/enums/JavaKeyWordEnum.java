package com.data.enums;

/**
 * @Description java的关键字
 * @Author tanlishuai
 * @Date 2020-06-24 15:43
 */
public enum JavaKeyWordEnum {

    PUBLIC("public","public1"),
    CLASS("class","class1"),
    ENUM("enum","enum1"),
    PACKAGE("package","package1"),
    PRIVATE("private","private1"),
    PROTECTED("protected","protected1"),
    DEFAULT("default","default1"),
    THIS("this","this1"),
    VOID("void","void1"),
    RETURN("return","return1"),
    STATIC("static","static1"),
    FINAL("final","final1"),
    TRY("try","try1"),
    CATCH("catch","catch1"),
    FINALLY("finally","finally1"),
    INTERFACE("interface","interface1"),
    IMPLEMENTS("implements","implements1"),
    EXTENDS("extends","extends1"),
    THROWS("throws","throws1"),
    THROW("throw","throw1"),
    NEW("new","new1"),
    ABSTRACT("abstract","abstract1"),
    CASE("case","case1"),
    CONTINUE("continue","continue1"),
    BREAK("break","break1"),
    FOR("for","for1"),
    INSTANCEOF("instanceof","instanceof1"),
    SWITCH("switch","switch1"),
    TRANSIENT("transient","transient1"),
    ASSERT("assert","assert1"),
    GOTO("goto","goto1"),
    INT("int","int1"),
    BYTE("byte","byte1"),
    SHORT("short","short1"),
    LONG("long","long1"),
    CHAR("char","char1"),
    BOOLEAN("boolean","boolean1"),
    DOUBLE("double","double1"),
    FLOAT("float","float1"),
    SYNCHRONIZED("synchronized","synchronized1"),
    DO("do","do1"),
    WHILE("while","while1"),
    IF("if","if1"),
    ELSE("else","else1"),
    STRICTFP("strictfp","strictfp1"),
    VOLATILE("volatile","volatile1"),
    CONST("const","const1"),
    IMPORT("import","import1"),
    NATIVE("native","native1"),
    SUPER("super","super1"),


    ;
    private String keyWord;
    private String changeName;

    JavaKeyWordEnum(String keyWord,String changeName){
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
