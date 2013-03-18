package com.soebes.tools.project.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class CheckOutCommand implements Command {
    private static Logger LOGGER = Logger.getLogger(CheckOutCommand.class);

    public static final String SVN_BASE_URL = "http://svnserver.company.com/";

    public List<SVNDirEntry> getFoldersFromSVN(SVNRepository repository, String url) throws SVNException {
        List<SVNDirEntry> folders = new ArrayList<SVNDirEntry>();

        repository.getDir(url, SVNRevision.HEAD.getNumber(), null, folders);
        return folders;
    }

    public SVNRepository createAuthenticationForRepository(String repository) throws SVNException {
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager();

        SVNRepository svnRepository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(SVN_BASE_URL + repository));

        svnRepository.setAuthenticationManager(authManager);

        return svnRepository;
    }

    public void doCheckoutModule(String repository, SVNURL url, File dstPath) throws SVNException {
        SVNRepository svnRepository = null;

        svnRepository = createAuthenticationForRepository(repository);

        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);

        SVNClientManager ourClientManager = SVNClientManager.newInstance(options, svnRepository.getAuthenticationManager());

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();

        updateClient.doCheckout(url, dstPath, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, false);

    }

    public boolean pathExists(SVNRepository svnRepository, String path) throws SVNException {
        boolean result = false;
        SVNNodeKind checkPath = svnRepository.checkPath(path, SVNRevision.HEAD.getNumber());
        if (checkPath.equals(SVNNodeKind.DIR)) {
            result = true;
        }

        return result;
    }

    /**
     * Check to see if the structure <code>tags</code>, <code>trunk</code> and
     * <code>branches</code> exists under the given folder.
     * 
     * @param svnRepository
     * @param svnDirEntry
     * @return true if the given structure exists false otherwise.
     * @throws SVNException
     */
    public boolean trunkTagsBranchesDoesExist(SVNRepository svnRepository, SVNDirEntry svnDirEntry) throws SVNException {
        //@TODO: Remove hard coded values. Put them into a configuration file.
        String trunkPath = svnRepository.getRepositoryPath(svnDirEntry.getRelativePath() + "/trunk");
        String tagsPath = svnRepository.getRepositoryPath(svnDirEntry.getRelativePath() + "/tags");
        String branchesPath = svnRepository.getRepositoryPath(svnDirEntry.getRelativePath() + "/branches");

        boolean result = pathExists(svnRepository, trunkPath) 
                && pathExists(svnRepository, tagsPath) 
                && pathExists(svnRepository, branchesPath);

        return result;
    }

    public void checkoutModuleBranch(SVNRepository svnRepository, SVNDirEntry svnDirEntry, String branch) {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager ourClientManager = SVNClientManager.newInstance(options, svnRepository.getAuthenticationManager());

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();

        // updateClient.doCheckout(url, dstPath, SVNRevision.HEAD.getNumber(),
        // SVNRevision.HEAD.getName(), SVNDepth.INFINITY, false);

    }

    @Override
    public void execute(CLIPTCommandLine command) {

        CLICheckoutCommand co = command.getCheckoutCommand();
        // co.getBranch();
        for (String repository : co.getRepositories()) {

            try {
                SVNRepository svnRepository = createAuthenticationForRepository(repository);
                LOGGER.info("Checking out from repository: " + repository);
                List<SVNDirEntry> moduleListFromSVN = getFoldersFromSVN(svnRepository, ".");
                for (SVNDirEntry svnDirEntry : moduleListFromSVN) {
                    LOGGER.info("Module: " + svnDirEntry.getName());
                    //
                    // modules.
                    // with TTB structure.

                    System.out.print("Module: " + svnDirEntry.getName() + " ");
                    if (!trunkTagsBranchesDoesExist(svnRepository, svnDirEntry)) {
                        System.out.println("Module does not contain trunk/tags/branches structure.");
                        continue;
                    }

                    // Now we have TTB structure

                    System.out.print("Checking out ");
                    checkoutModuleBranch(svnRepository, svnDirEntry, co.getBranch());
                }

            } catch (Exception e) {
                LOGGER.error("Failure during extraction.", e);
            }

            //
        }

    }

}
