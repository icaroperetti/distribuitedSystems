from datetime import datetime, timedelta


def get_time_in_seconds(time):
    hour, minute, second = time.split(':')
    seconds = (int(hour) * 3600) + (int(minute) * 60) + int(second)
    return seconds


def format_time(time):
    h, m, s = time
    return f'{h}:{m}:{s}'


def get_current_time():
    now = datetime.now()
    return (now.hour, now.minute, now.second)


def current_time_seconds():
    h, m, s = get_current_time()
    return((h*3600) + (m*60) + s)


def get_time_string(tempo):
    h, m, s = tempo
    return f'{h}:{m}:{s}'


def get_seconds_in_time(segundos):
    tempo = str(timedelta(seconds=segundos))
    h, m, s = tempo.split(':')
    return [h, m, s]


def seconds_to_time_string(seconds):
    s = seconds
    h = int(s // 3600)
    s = s % 3600
    m = int(s // 60)
    s = int(s % 60)
    return f'{h}:{m}:{s}'
