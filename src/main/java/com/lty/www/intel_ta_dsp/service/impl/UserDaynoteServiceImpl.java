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
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class UserDaynoteServiceImpl implements UserDaynoteService {

    @Autowired
    private UserDaynoteMapper userDaynoteMapper;

    @Override
    @Slave
    @Cacheable(value = "userDaynotes", key = "#dto.userId + '_' + #dto.date")
    public List<UserDaynote> selectBy(UserDaynoteDTO dto) {
        return userDaynoteMapper.selectBy(dto);
    }

    @Override
    @Master
    public boolean insert(UserDaynote userDaynote) {
        return userDaynoteMapper.insert(userDaynote);
    }

    @Override
    @Master
    public boolean update(UserDaynote userDaynote) {
        return userDaynoteMapper.update(userDaynote);
    }

    @Override
    @Master
    public boolean delete(UserDaynoteDTO dto) {
        return userDaynoteMapper.delete(dto);
    }

    @Override
    @Slave
    @Cacheable(value = "daynotesRange", key = "#dto.userId + '_' + #dto.startTime + '_' + #dto.endTime")
    public List<UserDaynote> findFromStartToEnd(AiDiaryDTO dto) {
        return userDaynoteMapper.findFromStartToEnd(dto);
    }
}
