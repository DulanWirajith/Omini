export class TimeAgo {

  timeSince(date) {

    let timeThen: any = new Date(date);
    let timeNow: any = new Date();

    let seconds = Math.floor((timeNow - timeThen) / 1000);

    let interval = seconds / 31536000;
    let moreThanOne = 's';
    if (interval > 1) {
      interval = Math.floor(interval);
      if (interval === 1) {
        moreThanOne = '';
      }
      return interval + " year" + moreThanOne + " ago";
    }
    interval = seconds / 2592000;
    if (interval > 1) {
      interval = Math.floor(interval);
      if (interval === 1) {
        moreThanOne = '';
      }
      return interval + " month" + moreThanOne + " ago";
    }
    interval = seconds / 86400;
    if (interval > 1) {
      interval = Math.floor(interval);
      if (interval === 1) {
        moreThanOne = '';
      }
      return interval + " day" + moreThanOne + " ago";
    }
    interval = seconds / 3600;
    if (interval > 1) {
      interval = Math.floor(interval);
      if (interval === 1) {
        moreThanOne = '';
      }
      return interval + " hour" + moreThanOne + " ago";
    }
    interval = seconds / 60;
    if (interval > 1) {
      interval = Math.floor(interval);
      if (interval === 1) {
        moreThanOne = '';
      }
      return interval + " minute" + moreThanOne + " ago";
    }
    seconds = Math.floor(seconds);
    if (seconds === 1) {
      moreThanOne = '';
    }
    return seconds + " second" + moreThanOne + " ago";
  }
}
