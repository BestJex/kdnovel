package com.keduw.service.impl;

import com.github.pagehelper.PageHelper;
import com.keduw.dao.ChapterMapper;
import com.keduw.jedis.JedisClient;
import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service.impl
 * @ClassName: ChapterServiceImpl
 * @Description: 小说信息
 * @Author: 林浩东
 */
@Service("chapterService")
@PropertySource("classpath:cache.properties")
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("chapter")
    private String keys;

    //插入章节信息
    @Override
    public void insertChapter(List<Chapter> chapterList) {
        if(chapterList != null && chapterList.size() > 0){
            chapterMapper.insertChapter(chapterList);
            //清除对应小说的章节缓存
            int novelId = chapterList.get(0).getNovelId();
            String field = "chapter" + novelId;
            jedisClient.hdel(keys, field);
        }
    }


    //更新章节列表
    @Override
    public void updateChapter(List<Chapter> chapterList) {
        if(chapterList != null && chapterList.size() > 0){
            chapterMapper.updateChapter(chapterList);
            //清除对应小说的章节缓存
            int novelId = chapterList.get(0).getNovelId();
            String field = "chapter" + novelId;
            jedisClient.hdel(keys, field);
        }
    }


    //通过小说id返回章节列表
    @Override
    public List<Chapter> getChapterList(int NovelId) {
        List<Chapter> list = new ArrayList<Chapter>();
        String field = "chapter" + NovelId;
        String info = jedisClient.hget(keys, field);
        System.out.println(NovelId);
        if(info != null && !info.isEmpty()){
            list = JsonUtils.jsonToList(info, Chapter.class);
        }else{
            list = chapterMapper.selectInfoByNovelId(NovelId);
            //添加数据到redis中
            if(list != null && list.size() > 0){
                jedisClient.hset(keys, field, JsonUtils.objectToJson(list));
            }
        }
        return list;
    }

    //获取章节总数
    @Override
    public int getInfoCounts() {
        return chapterMapper.selectCounts();
    }

    //查询章节内容为空的数据
    @Override
    public List<Chapter> getChapterList(int start, int size) {
        PageHelper.startPage(start, size);
        List<Chapter> list = chapterMapper.selectInfoByContent();
        return list;
    }

}