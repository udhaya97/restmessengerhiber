package com.messengerapphiber.dao;

import java.util.List;

import com.messengerapphiber.model.Profile;

public interface ProfileDao {
	
	
	int savProfile(Profile profile);
	List<Profile> getAllProfiles();
	Profile getProfile(String profName);
	
	int updateProfile(Profile profile);
	int deleteProfile(String profName);
	

}
