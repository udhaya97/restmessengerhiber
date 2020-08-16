package com.messengerapphiber.dao;

import java.util.List;

import com.messengerapphiber.model.Comment;

public interface CommentDao {
	
	Comment getComms(int messId);
	
	int saveComms(Comment comment);
	
	int updateComms(Comment comment);
	
	int deleComms(int messId,int commId);

}
