package com.lty.www.intel_ta_dsp.service;


import com.lty.www.intel_ta_dsp.dto.UserDaynoteDTO;
import com.lty.www.intel_ta_dsp.entity.UserDaynote;

import java.util.List;

public interface UserDaynoteService {
    List<UserDaynote> selectBy(UserDaynoteDTO dto);

    boolean insert(UserDaynote userDaynote);

    boolean update(UserDaynote userDaynote);

    boolean delete(UserDaynoteDTO dto);
}
