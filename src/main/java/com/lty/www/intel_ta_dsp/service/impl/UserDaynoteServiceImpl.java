package com.lty.www.intel_ta_dsp.service.impl;


import com.lty.www.intel_ta_dsp.dto.UserDaynoteDTO;
import com.lty.www.intel_ta_dsp.dto.aidto.AiDiaryDTO;
import com.lty.www.intel_ta_dsp.entity.UserDaynote;
import com.lty.www.intel_ta_dsp.mapper.UserDaynoteMapper;
import com.lty.www.intel_ta_dsp.service.UserDaynoteService;
import com.lty.www.intel_ta_dsp.datasource.Master;
import com.lty.www.intel_ta_dsp.datasource.Slave;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class UserDaynoteServiceImpl implements UserDaynoteService {

    @Autowired
    private UserDaynoteMapper userDaynoteMapper;

    // ===================== 读方法 =====================
    @Override
    @Slave
    @Cacheable(value = "userDaynotes", key = "#dto.userId + '_' + #dto.date")
    public List<UserDaynote> selectBy(UserDaynoteDTO dto) {
        System.out.println("[CACHE READ] 查询 userDaynotes, userId={}, date={}"+ dto.getUserId()+ dto.getDate());
        List<UserDaynote> list = userDaynoteMapper.selectBy(dto);
        System.out.println("[CACHE READ] 查询结果: {}"+list);
        return list;
    }

    @Override
    @Slave
    public List<UserDaynote> findFromStartToEnd(AiDiaryDTO dto) {
        return userDaynoteMapper.findFromStartToEnd(dto);
    }

    // ===================== 写方法 =====================
    @Override
    @Master
    @Caching(evict = {
            @CacheEvict(value = "userDaynotes", key = "#userDaynote.userId + '_' + #userDaynote.date"),
            @CacheEvict(value = "daynotesRange", allEntries = true)
    })
    public boolean insert(UserDaynote userDaynote) {
        return userDaynoteMapper.insert(userDaynote);
    }

    @Override
    @Master
    @Caching(evict = {
            @CacheEvict(value = "userDaynotes", key = "#userDaynote.userId + '_' + #userDaynote.date"),
            @CacheEvict(value = "daynotesRange", allEntries = true)
    })
    public boolean update(UserDaynote userDaynote) {
        return userDaynoteMapper.update(userDaynote);
    }

    @Override
    @Master
    @Caching(evict = {
            @CacheEvict(value = "userDaynotes", key = "#dto.userId + '_' + #dto.date"),
            @CacheEvict(value = "daynotesRange", allEntries = true)
    })
    public boolean delete(UserDaynoteDTO dto) {
        return userDaynoteMapper.delete(dto);
    }
}
