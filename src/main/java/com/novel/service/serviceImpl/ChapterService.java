package com.novel.service.serviceImpl;

import com.novel.dao.ChapterMapper;
import com.novel.model.Chapter;
import com.novel.service.ChapterIService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service.serviceImpl
 * @ClassName: ChapterService
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/12/012 1:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/12/012 1:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Service("ChapterService")
public class ChapterService implements ChapterIService {
    @Resource
    private ChapterMapper chapterMapper;
    @Override
    public void insertChapter(Chapter chapter) {
        chapterMapper.insertChapter0(chapter);
    }

    @Override
    public List<Chapter> findByNovelIdChapter(long NovelId) {
        return chapterMapper.findByNovelIdChapter0(NovelId);
    }

    @Override
    public void updateChapter(Chapter chapter) {
        chapterMapper.updateChapter0(chapter);
    }
}
