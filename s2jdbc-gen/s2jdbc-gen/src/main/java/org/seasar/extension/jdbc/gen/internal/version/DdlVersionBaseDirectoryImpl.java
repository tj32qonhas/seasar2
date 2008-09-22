/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.extension.jdbc.gen.internal.version;

import java.io.File;

import org.seasar.extension.jdbc.gen.internal.exception.NextVersionDirectoryExistsRuntimeException;
import org.seasar.extension.jdbc.gen.version.DdlInfoFile;
import org.seasar.extension.jdbc.gen.version.DdlVersionBaseDirectory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringConversionUtil;

/**
 * {@link DdlVersionBaseDirectory}の実装クラスです。
 * 
 * @author taedium
 */
public class DdlVersionBaseDirectoryImpl implements DdlVersionBaseDirectory {

    /** createディレクトリの名前 */
    protected static String CREATE_DIR_NAME = "create";

    /** dropディレクトリの名前 */
    protected static String DROP_DIR_NAME = "drop";

    /** ロガー */
    protected static Logger logger = Logger
            .getLogger(DdlVersionBaseDirectoryImpl.class);

    /** バージョン管理のベースディレクトリ */
    protected File baseDir;

    /** バージョン番号のパターン */
    protected String versionNoPattern;

    /** DDLのバージョン */
    protected DdlInfoFile ddlInfoFile;

    /** 現在のバージョンディレクトリ */
    protected File currentVersionDir;

    /** 次のバージョンディレクトリ */
    protected File nextVersionDir;

    /**
     * インスタンスを構築します。
     * 
     * @param baseDir
     *            バージョン管理のベースディレクトリ
     * @param versionFile
     *            バージョンファイル
     * @param versionNoPattern
     *            バージョン番号のパターン
     */
    public DdlVersionBaseDirectoryImpl(File baseDir, File versionFile,
            String versionNoPattern) {
        if (baseDir == null) {
            throw new NullPointerException("baseDir");
        }
        if (versionFile == null) {
            throw new NullPointerException("versionFile");
        }
        if (versionNoPattern == null) {
            throw new NullPointerException("versionNoPattern");
        }
        this.baseDir = baseDir;
        this.versionNoPattern = versionNoPattern;
        ddlInfoFile = createDdlInfoFile(versionFile);

        String currentVersionDirName = getVersionDirName(ddlInfoFile
                .getCurrentVersionNo(), versionNoPattern);
        currentVersionDir = new File(baseDir, currentVersionDirName);

        String nextVersionDirName = getVersionDirName(ddlInfoFile
                .getNextVersionNo(), versionNoPattern);
        nextVersionDir = new File(baseDir, nextVersionDirName);
        if (nextVersionDir.exists()) {
            throw new NextVersionDirectoryExistsRuntimeException(nextVersionDir
                    .getPath(), versionFile.getPath());
        }
    }

    public File getCurrentVersionDir() {
        return currentVersionDir;
    }

    public File getNextVersionDir() {
        return nextVersionDir;
    }

    public File getVersionDir(int versionNo) {
        return new File(baseDir, getVersionDirName(versionNo, versionNoPattern));
    }

    public File getCreateDir(File versionDir) {
        return new File(versionDir, CREATE_DIR_NAME);
    }

    public File getDropDir(File versionDir) {
        return new File(versionDir, DROP_DIR_NAME);
    }

    public DdlInfoFile getDdlInfoFile() {
        return ddlInfoFile;
    }

    /**
     * {@link DdlInfoFile}の実装を作成します。
     * 
     * @param file
     *            ファイル
     * @return {@link DdlInfoFile}の実装
     */
    protected DdlInfoFile createDdlInfoFile(File file) {
        return new DdlInfoFileImpl(file);
    }

    /**
     * バージョンディレクトリの名前を返します。
     * 
     * @param versionNo
     *            バージョン番号
     * @param pattern
     *            バージョン番号のパターン
     * @return バージョンディレクトリの名前
     */
    protected String getVersionDirName(int versionNo, String pattern) {
        return StringConversionUtil.toString(versionNo, pattern);
    }
}