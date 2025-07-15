package com.lty.www.intel_ta_dsp.service.impl;


import com.lty.www.intel_ta_dsp.dto.UserDaynoteDTO;
import com.lty.www.intel_ta_dsp.entity.UserDaynote;
import com.lty.www.intel_ta_dsp.mapper.UserDaynoteMapper;
import com.lty.www.intel_ta_dsp.service.UserDaynoteService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class UserDaynoteServiceImpl implements UserDaynoteService {

    private UserDaynoteMapper userDaynoteMapper;

    @Override
    public List<UserDaynote> selectBy(UserDaynoteDTO dto) {
        return userDaynoteMapper.selectBy(dto);
    }

    @Override
    public boolean insert(UserDaynote userDaynote) {
        return userDaynoteMapper.insert(userDaynote);
    }

    @Override
    public boolean update(UserDaynote userDaynote) {
        return userDaynoteMapper.update(userDaynote);
    }

    @Override
    public boolean delete(UserDaynoteDTO dto) {
        return userDaynoteMapper.delete(dto);
    }
}
