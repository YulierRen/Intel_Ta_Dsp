package com.lty.www.intel_ta_dsp.mapper;

import com.lty.www.intel_ta_dsp.entity.UserDiary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDiaryMapper {
    // 查询所有日记（已有方法）
    List<UserDiary> findAll();

    // 根据用户ID查询日记
    List<UserDiary> findByUserId(Long userId);

    // 插入新日记
    int insert(UserDiary diary);

    // 根据ID设置is_public属性
    int updateIsPublic(@Param("id") Long id, @Param("isPublic") Boolean isPublic);

    // 根据ID删除日记
    int deleteById(Long id);

    //传diary，改内容
    boolean updateDiary(UserDiary diary);

}