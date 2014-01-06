package com.tentelemed.archipel.core.application.service;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 19/12/13
 * Time: 12:20
 */
public interface CommandHandler<CMD extends Command> {
    CmdRes handle(CMD cmd);
}
