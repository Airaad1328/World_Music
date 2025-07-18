package com.gmail.clarkin200.WorldMusicDemo.service;

import com.gmail.clarkin200.WorldMusicDemo.model.HubSession;

public interface HubService extends BaseService<HubSession,Long>{
    HubSession addUserToHubById (Long userId,Long hubId);
    HubSession deleteUserFromHubById (Long userId,Long hubId);
    HubSession joinHubSession (Long hubSessionId);
}
