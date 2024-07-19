# Windows 環境でのソフトウェアのインストール

Scoop を使って必要なソフトウェアをインストールする方法を紹介します。

## 1. PowerShell の起動方法

Windows では、**PowerShell**とよばれるシェルが標準で搭載されています。

PowerShell を起動するには、スタートボタン（左下にある Windows のロゴ）を右クリックするか、`Win-X` キーを押して以下のメニューを表示し、「Windows PowerShell (管理者)(A)」をクリックしてください。

![image](https://user-images.githubusercontent.com/298748/115985113-42199a00-a5e5-11eb-9f7c-85c19f73666b.png)

## 2. Scoop を用いた環境構築

**パッケージ管理ツール**と呼ばれる、ソフトウェアのインストールを簡単にするためのツールをインストールします。
[Scoop](https://scoop.sh/) を用いた環境構築を推奨します。

Scoop をインストールするには、PowerShell を**管理者権限**で起動し、以下のコマンドを入力します。

```powershell
iwr -useb get.scoop.sh | iex
```

インストールに失敗する際は、以下のコマンドを入力してから再度上のコマンドを入力してください。

```powershell
Set-ExecutionPolicy RemoteSigned -scope CurrentUser
```

これらの操作を行うためには、ユーザーアカウントに [管理者権限](https://support.microsoft.com/ja-jp/windows/63267a09-9926-991a-1c77-d203160c8563) があることが前提となります。

## 3. 必要なソフトウェアのインストール

Railway を進めるには、**Git**、**node**、**yarn**, **Java** のインストールが必要です。管理者権限で起動した PowerShell に以下のコマンドを入力して、Scoop を経由してインストールしましょう。

```powershell
scoop install git nodejs-lts yarn adopt21-hotspot
```
稀に[adopt11-hotspotが失敗する事例](https://github.com/TechTrain-Community/RailwayForum/discussions/605)が確認されています。その場合、[手動インストール](https://sukkiri.jp/technologies/processors/jdk/adoptopenjdk11-win_install.html)してお試ししましょう。
