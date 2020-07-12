"""Utility functions for tomcat.
This library is for Robot Framework.

"""

import os
import sys
import shutil
import time
import stat
import zipfile

class TomcatLibrary:

    def __init__(self, location, os_type='windows'):
        self.location = location
        self.os_type = os_type
        if False == os.path.isdir(location):
            raise AssertionError("tomcat at [" + location + '[ is not available')

    def start_tomcat(self):
        print 'starting tomcat at ' + self.location + ',os is ' + self.os_type 
        os.putenv('CATALINA_HOME', self.location)
        
        if self.os_type == 'windows':
            startup_shell = os.path.join(self.location, 'bin', 'startup.bat')
        else:
            startup_shell = os.path.join(self.location, 'bin', 'startup.sh')
            
        if 0 != os.system(startup_shell):
            raise AssertionError("Starting failed")
            
        
    def shutdown_tomcat(self):
        print 'shuting down'
        os.putenv('CATALINA_HOME', self.location)
        
        if self.os_type == 'windows' :
            shutdown_shell = os.path.join(self.location, 'bin', 'shutdown.bat')
        else:
            shutdown_shell = os.path.join(self.location, 'bin', 'shutdown.sh')
        os.system(shutdown_shell)
        time.sleep(3)
        
    def clean_log(self):
        print 'clearing logs...'
        logsLocation = os.path.join(self.location, "logs")
        self._clean_folder (logsLocation)
        
    def clean_workfolder(self):
        print 'clean workfolder'
        workfolder = os.path.join(self.location, "work", "Catalina")
        if os.path.isdir(workfolder):
            shutil.rmtree(workfolder)

    def deploy_war(self, warfile, dest='COMP_Portal'):
        war = zipfile.ZipFile(warfile)
        war_dest = os.path.join(self.location, 'webapps', dest)
        if os.path.isdir(war_dest) :
            self._clean_folder(war_dest)
        else:
            os.makedirs(war_dest)
        war.extractall(war_dest)
        
        
    def _clean_folder(self, path, ignore_errors=False, onerror=None):
        if ignore_errors:
            def onerror(*args):
                pass
        elif onerror is None:
            def onerror(*args):
                raise
        try:
            if os.path.islink(path):
                # symlinks to directories are forbidden, see bug #1669
                raise OSError("Cannot call rmtree on tomcat symbolic link")
        except OSError:
            onerror(os.path.islink, path, sys.exc_info())
            return
        names = []
        try:
            names = os.listdir(path)
        except os.error, err:
            onerror(os.listdir, path, sys.exc_info())
        for name in names:
            fullname = os.path.join(path, name)
            try:
                mode = os.lstat(fullname).st_mode
            except os.error:
                mode = 0
            if stat.S_ISDIR(mode):
                shutil.rmtree(fullname, ignore_errors, onerror)
            else:
                try:
                    os.remove(fullname)
                except os.error, err:
                    onerror(os.remove, fullname, sys.exc_info())
