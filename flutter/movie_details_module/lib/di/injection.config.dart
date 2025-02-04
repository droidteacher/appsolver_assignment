// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

// ignore_for_file: type=lint
// coverage:ignore-file

// ignore_for_file: no_leading_underscores_for_library_prefixes
import 'package:flutter/services.dart' as _i4;
import 'package:get_it/get_it.dart' as _i1;
import 'package:injectable/injectable.dart' as _i2;
import 'package:movie_details_module/channel_provider.dart' as _i5;
import 'package:movie_details_module/data/remote/movie_datasource.dart' as _i3;

extension GetItInjectableX on _i1.GetIt {
// initializes the registration of main-scope dependencies inside of GetIt
  _i1.GetIt init({
    String? environment,
    _i2.EnvironmentFilter? environmentFilter,
  }) {
    final gh = _i2.GetItHelper(
      this,
      environment,
      environmentFilter,
    );
    final channelProvider = _$ChannelProvider();
    gh.factory<_i3.MovieDatasource>(() => _i3.TmdbApi());
    gh.factory<String>(
      () => channelProvider.channelName,
      instanceName: 'MovieFactsChannel',
    );
    gh.lazySingleton<_i4.MethodChannel>(() => channelProvider
        .provideChannel(gh<String>(instanceName: 'MovieFactsChannel')));
    return this;
  }
}

class _$ChannelProvider extends _i5.ChannelProvider {}
