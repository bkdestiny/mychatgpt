package com.mychatgpt.util;

public class RedisContent {
    /*用户校验*/
    public static final String TOKEN_KEY="mc:token:";
    public static final Long TOKEN_TTL=7L;
    public static final Long TRYLOCK_TTL=10L;
}
