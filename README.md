# 環境構築

1. AndroidStudio
2. Java（v11以降）
3. Git
4. Node.js（v20.0.0以降）
5. Yarn (v1)

上記をインストールする必要があります。

Git、Node、Yarn のインストール方法やバージョンの確認については、[Railway 準備編](https://www.notion.so/techbowl/Railway-ceba695d5014460e9733c2a46318cdec) をご確認ください。※ 開発エディタ（Visual Studio Code）と、GitHub Codespaces についての資料はスキップしてください。

その他リポジトリの更新の仕方や、トラブルシューティングについても上記の Railway 準備編にございます。
何かあった際は、まずそちらを確認しましょう。

## 1. AndroidStudio のインストール
[https://developer.android.com/studio](https://developer.android.com/studio) よりご自身の PC にあった AndroidStudio をインストールしてください。

## 2. Java のインストール

### 2-1 macOS の場合
ターミナルを起動し、以下のコマンドを実行してください。（Homebrew を経由してインストールする。）

```shell
brew install node yarn openjdk
```

### 2-2 Windows の場合
管理者権限で PowerShell を起動し、以下のコマンドを実行してください。（Scoop を経由してインストールする。）

```powershell
scoop install git nodejs-lts yarn adopt21-hotspot
```

稀に [adopt11-hotspotが失敗する事例](https://github.com/TechTrain-Community/RailwayForum/discussions/605) が確認されています。
その場合は [手動インストール](https://sukkiri.jp/technologies/processors/jdk/adoptopenjdk11-win_install.html) を試してみてください。

# トラブルシューティング

## Android Studio のインストール時に HAXM のインストールに失敗する場合

Windows かつエミュレーターを使用する方が対象になります。(Mac を使う方、実機を使用してアプリの動作を確認する方は HAXM のインストールに失敗していても問題ありません)

Android Studio のセットアップ中に以下のようなエラーが表示された場合は Windows 側で仮想化支援機能を有効にする必要があります。

```
This computer does not support Intel Virtualization Technology (VT-x) or it is being exclusively used by Hyper-V. HAXM cannot be installed.
Please ensure Hyper-V is disabled in Windows Features, or refer to the Intel HAXM documentation for more information.
```

以下の記事を参考に、Hyper-V を有効化してください。

https://docs.microsoft.com/ja-jp/virtualization/hyper-v-on-windows/quick-start/enable-hyper-v
