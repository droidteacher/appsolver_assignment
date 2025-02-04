import 'package:flutter/services.dart';
import 'package:injectable/injectable.dart';

@module
abstract class ChannelProvider {
  @Named('MovieFactsChannel')
  String get channelName => 'hu.zsoltkiss.moviefacts/channel';

  @lazySingleton
  MethodChannel provideChannel(@Named('MovieFactsChannel') String name) => MethodChannel(name);
}