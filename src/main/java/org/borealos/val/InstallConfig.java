package org.borealos.val;

public class InstallConfig {
    private String desktopEnvironment;

    private boolean installBash;
    private boolean installFish;
    private boolean installSh;

    private boolean kernelLTS;
    private boolean kernelStd;

    public void setDesktopEnvironment(String desktopEnvironment) {
        this.desktopEnvironment = desktopEnvironment;
    }

    public void setInstallBash(boolean installBash) {
        this.installBash = installBash;
    }

    public void setInstallFish(boolean installFish) {
        this.installFish = installFish;
    }

    public void setInstallSh(boolean installSh) {
        this.installSh = installSh;
    }

    public void setKernelLTS(boolean kernelLTS) {
        this.kernelLTS = kernelLTS;
    }

    public void setKernelStd(boolean kernelStd) {
        this.kernelStd = kernelStd;
    }

    public String getDesktopEnvironment() {
        return desktopEnvironment;
    }

    public boolean isInstallBash() {
        return installBash;
    }

    public boolean isInstallFish() {
        return installFish;
    }

    public boolean isInstallSh() {
        return installSh;
    }

    public boolean isKernelLTS() {
        return kernelLTS;
    }

    public boolean isKernelStd() {
        return kernelStd;
    }

}
