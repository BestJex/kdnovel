package com.novel.dao;

import com.novel.model.Chapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.dao
 * @ClassName: ChapterMapper
 * @Description: java类作用描述:章节信息持久层接口
 * @Author: 林浩东
 * @CreateDate: 2018/8/12/012 1:34
 * @UpdateUser: 林浩东
 * @UpdateDate: 2018/8/12/012 1:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Mapper
public interface ChapterMapper {

    void insertChapter0(Chapter chapter);
    List<Chapter> findByNovelIdChapter0(long NovelId);
    void updateChapter0(Chapter chapter);

}
