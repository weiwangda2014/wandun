package com.bos.wandun;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 消息
 *
 * @author Hello King
 * @version 4.0
 */
public class Message implements Serializable {

    /**
     * 类型
     */
    public enum Type {

        /**
         * 成功
         */
        success,

        /**
         * 警告
         */
        warn,

        /**
         * 错误
         */
        error
    }

    /**
     * 类型
     */
    private Type type;

    /**
     * 内容
     */
    private String content;


    private Object data;

    /**
     * 构造方法
     */
    public Message() {
    }

    /**
     * 构造方法
     *
     * @param type    类型
     * @param content 内容
     */
    public Message(Type type, String content) {
        this.type = type;
        this.content = content;
    }


    /**
     * 构造方法
     *
     * @param type    类型
     * @param content 内容
     */
    public Message(Type type, String content, Object data) {
        this.type = type;
        this.content = content;
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param type    类型
     * @param content 内容
     * @param args    参数
     */
    public Message(Type type, String content, Object data, Object... args) {
        this.type = type;
        this.content = content;
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @param content 内容
     * @param args    参数
     * @return 成功消息
     */
    public static Message success(String content, Object... args) {
        return new Message(Type.success, content, args);
    }

    public static Message success(String content, Object data) {
        return new Message(Type.success, content, data);
    }


    public static Message success(String content, Object data, Object... args) {
        return new Message(Type.success, content, data, args);
    }

    /**
     * 返回警告消息
     *
     * @param content 内容
     * @param args    参数
     * @return 警告消息
     */
    public static Message warn(String content, Object... args) {
        return new Message(Type.warn, content, args);
    }

    public static Message warn(String content, Object data, Object... args) {
        return new Message(Type.warn, content, data, args);
    }

    /**
     * 返回错误消息
     *
     * @param content 内容
     * @param args    参数
     * @return 错误消息
     */
    public static Message error(String content, Object... args) {
        return new Message(Type.error, content, args);
    }

    public static Message error(String content, Object data, Object... args) {
        return new Message(Type.error, content, data, args);
    }

    /**
     * 获取类型
     *
     * @return 类型
     */
    public Type getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 获取内容
     *
     * @return 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 重写toString方法
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}